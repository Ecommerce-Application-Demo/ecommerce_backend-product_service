package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.response.*;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.Sku;
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
    SkuRepo skuRepo;
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
                        res.setSku(getSku(productDto1.getProductId().toString(), null, null, null));
                        return res;
                    }).toList();
        }else {
            productDto = productRepo.findProductById_Name(productName, productId).stream()
                    .map(productDto1 -> {
                        ProductResponse res = modelMapper.map(productDto1, ProductResponse.class);
                        res.setSku(getSku(productDto1.getProductId().toString(), null, null, null));
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
    public List<SkuDto> getSku(String productId, String skuId, String size, String colour) {

        return skuRepo.findSKU(productId,skuId,size,colour).stream()
                .map(sku -> {
                                sku.setSizeVariantDetails(sku.getSizeVariantDetails().stream().toList());
                               SkuDto skuDto1= modelMapper.map(sku,SkuDto.class);
                               skuDto1.setProductId(sku.getProduct().getProductId());
                               return skuDto1;
                }).toList();
    }

    @Override
    public List<SizeInfo> getSizes(String skuId){
        Sku sku = skuRepo.findById(skuId).orElseGet(Sku::new);
        List<SizeInfo> sizes = new ArrayList<>() ;
        if(!sku.getSizeVariantDetails().isEmpty()) {
            sku.getSizeVariantDetails().forEach(sizeVariantDetails -> {
                    sizes.add(new SizeInfo(sizeVariantDetails.getSkuSizeId(),sizeVariantDetails.getSize(),sizeVariantDetails.getQuantity()));
            });
        }
        return sizes;
    }

    @Override
    public Set<ColourInfo> getColours(String productId){
        List<Sku> skuList=skuRepo.findSKU(productId,null,null,null);
        Set<ColourInfo> colourInfos=new HashSet<>();
        if(!skuList.isEmpty()){
            skuList.forEach(sku -> {
                AtomicBoolean flag= new AtomicBoolean(false);
                getSizes(sku.getSkuId()).forEach(size -> {
                    if (size.quantity() > 0)
                        flag.set(true);
                });
                if(flag.get())
                    colourInfos.add(new ColourInfo(sku.getSkuId(),sku.getColour(),sku.getImages().getDefaultImage(),true));
                else
                    colourInfos.add(new ColourInfo(sku.getSkuId(),sku.getColour(),sku.getImages().getDefaultImage(),false));
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
                        getSku(product.getProductId().toString(), null, null, null)
                                .forEach( skuDto -> {
                                    res.setSkuId(skuDto.getSkuId());
                                    res.setSkuName(skuDto.getSkuName());
                                    res.setColour(skuDto.getColour());
                                    res.setMrp(skuDto.getMrp());
                                    res.setDiscountPercentage(skuDto.getDiscountPercentage());
                                    res.setFinalPrice(skuDto.getFinalPrice());
                                    res.setImages(skuDto.getImages());
                                    productListingResponse.add(res);
                                });
                  }
                );
        return productListingResponse;
    }
}

