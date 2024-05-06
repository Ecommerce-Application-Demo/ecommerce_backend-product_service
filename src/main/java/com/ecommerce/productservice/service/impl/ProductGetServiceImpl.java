package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.response.ColourInfo;
import com.ecommerce.productservice.dto.response.ProductListingResponse;
import com.ecommerce.productservice.dto.response.ProductResponse;
import com.ecommerce.productservice.dto.response.SizeInfo;
import com.ecommerce.productservice.entity.ProductStyleVariant;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;
import com.ecommerce.productservice.repository.*;
import com.ecommerce.productservice.service.declaration.ProductGetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service

public class ProductGetServiceImpl implements ProductGetService {
    @Autowired
    MasterCategoryRepo masterCategoryRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    SubCategoryRepo subCategoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ReviewRatingRepo reviewRatingRepo;
    @Autowired
    StyleVariantRepo styleVariantRepo;
    @Autowired
    InventoryRepo inventoryRepo;
    @Autowired
    WarehouseRepo warehouseRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<MasterCategoryDto> getMasterCategory(String masterCategoryId, String masterCategoryName) {
        return masterCategoryRepo.findMasterCategory(masterCategoryName, masterCategoryId)
                .stream().map(masterCategory -> modelMapper.map(masterCategory, MasterCategoryDto.class)).toList();
    }

    @Override
    public List<CategoryDto> getCategory(String categoryName, String categoryId, String masterCategory) {
        List<CategoryDto> categoryDto;
        if (categoryName != null || categoryId != null || masterCategory != null) {
            categoryDto = categoryRepo.findCategory(categoryName, categoryId, masterCategory).stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        } else {
            categoryDto = categoryRepo.findAll().stream()
                    .map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        }
        return categoryDto;
    }

    @Override
    public List<SubCategoryDto> getSubCategory(String subCategoryName, String subCategoryId, String categoryName) {
        List<SubCategoryDto> subCategoryDto;
        if (subCategoryName != null || subCategoryId != null || categoryName != null) {
            subCategoryDto = subCategoryRepo.findSubCategory(subCategoryName, subCategoryId, categoryName).stream()
                    .map(subCategory -> modelMapper.map(subCategory, SubCategoryDto.class)).toList();
        } else {
            subCategoryDto = subCategoryRepo.findAll().stream()
                    .map(subCategory -> modelMapper.map(subCategory, SubCategoryDto.class)).toList();
        }
        return subCategoryDto;
    }

    @Override
    public List<BrandDto> getBrand() {
        return brandRepo.findAll().stream()
                .map(brand -> modelMapper.map(brand, BrandDto.class)).toList();
    }

    @Override
    public List<ProductResponse> getProduct(String productId, String productName, String subCategoryName,
                                            String categoryName, String masterCategoryName, String brand, String gender) {
        List<ProductResponse> productDto;
        if (masterCategoryName != null || subCategoryName != null || categoryName != null || brand != null || gender != null) {
            productDto = productRepo.findProductByCategory(subCategoryName, categoryName, masterCategoryName, brand, gender).stream()
                    .map(productDto1 -> {
                        ProductResponse res = modelMapper.map(productDto1, ProductResponse.class);
                        res.setStyleVariants(getStyleVariants(productDto1.getProductId().toString(), null, null, null));
                        return res;
                    }).toList();
        }else {
            productDto = productRepo.findProductById_Name(productName, productId).stream()
                    .map(productDto1 -> {
                        ProductResponse res = modelMapper.map(productDto1, ProductResponse.class);
                        res.setStyleVariants(getStyleVariants(productDto1.getProductId().toString(), null, null, null));
                        return res;
                    }).toList();
        }
        return productDto;
    }

    @Override
    public List<ReviewRating> getReview(UUID productId) {
        return reviewRatingRepo.findAllByProductId(productId);
    }

    @Override
    public List<StyleVariantDetailsDto> getStyleVariants(String productId, String styleId, String size, String colour) {

        return styleVariantRepo.findStyle(productId,styleId,size,colour).stream()
                .map(sku -> {
                                sku.setSizeDetails(sku.getSizeDetails().stream().toList());
                               StyleVariantDetailsDto styleVariantDetailsDto1 = modelMapper.map(sku, StyleVariantDetailsDto.class);
                               styleVariantDetailsDto1.setProductId(sku.getProduct().getProductId());
                               return styleVariantDetailsDto1;
                }).toList();
    }

    @Override
    public List<SizeInfo> getSizes(String styleId){
        ProductStyleVariant productStyleVariant = styleVariantRepo.findById(styleId).orElseGet(ProductStyleVariant::new);
        List<SizeInfo> sizes = new ArrayList<>() ;
        if(!productStyleVariant.getSizeDetails().isEmpty()) {
            productStyleVariant.getSizeDetails().forEach(sizeDetails -> {
                    sizes.add(new SizeInfo(sizeDetails.getSizeId(),sizeDetails.getSize(),sizeDetails.getQuantity()));
            });
        }
        return sizes;
    }

    @Override
    public Set<ColourInfo> getColours(String productId){
        List<ProductStyleVariant> productStyleVariantList = styleVariantRepo.findStyle(productId,null,null,null);
        Set<ColourInfo> colourInfos=new HashSet<>();
        if(!productStyleVariantList.isEmpty()){
            productStyleVariantList.forEach(psv -> {
                AtomicBoolean flag= new AtomicBoolean(false);
                getSizes(psv.getStyleId()).forEach(size -> {
                    if (size.quantity()!= null && size.quantity() > 0)
                        flag.set(true);
                });
                if(flag.get())
                    colourInfos.add(new ColourInfo(psv.getStyleId(),psv.getColour(),psv.getImages().getDefaultImage(),true));
                else
                    colourInfos.add(new ColourInfo(psv.getStyleId(),psv.getColour(),psv.getImages().getDefaultImage(),false));
            });
        }
        return colourInfos;
    }

    @Override
    public Set<ProductListingResponse> getProductListing(String subCategoryName, String categoryName, String masterCategoryName, String brand, String gender) {

        Set<ProductListingResponse> productListingResponse = new HashSet<>();

        productRepo.findProductByCategory(subCategoryName, categoryName, masterCategoryName, brand, gender)
                  .forEach(product -> {
                        ProductListingResponse res = modelMapper.map(product,ProductListingResponse.class);
                        getStyleVariants(product.getProductId().toString(), null, null, null)
                                .forEach(styleVariantDetailsDto -> {
                                    res.setSkuId(styleVariantDetailsDto.getStyleId());
                                    res.setSkuName(styleVariantDetailsDto.getStyleName());
                                    res.setColour(styleVariantDetailsDto.getColour());
                                    res.setMrp(styleVariantDetailsDto.getMrp());
                                    res.setDiscountPercentage(styleVariantDetailsDto.getDiscountPercentage());
                                    res.setFinalPrice(styleVariantDetailsDto.getFinalPrice());
                                    res.setImages(styleVariantDetailsDto.getImages());
                                    productListingResponse.add(res);
                                });
                  }
                );
        return productListingResponse;
    }

    @Override
    public List<Warehouse> getWarehouse(Integer warehouseId) {
        if(warehouseId!=null){
            return List.of(warehouseRepo.findById(warehouseId).orElseThrow());
        }else{
            return warehouseRepo.findAll();
        }

    }
}

