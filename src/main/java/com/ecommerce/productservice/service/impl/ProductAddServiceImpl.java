package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.request.CategoryRequest;
import com.ecommerce.productservice.dto.request.ProductRequest;
import com.ecommerce.productservice.dto.request.StyleVariantRequest;
import com.ecommerce.productservice.dto.request.SubCategoryRequest;
import com.ecommerce.productservice.entity.*;
import com.ecommerce.productservice.entity.warehousemanagement.Inventory;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;
import com.ecommerce.productservice.exceptionhandler.ErrorCode;
import com.ecommerce.productservice.exceptionhandler.ProductException;
import com.ecommerce.productservice.repository.*;
import com.ecommerce.productservice.service.declaration.ProductAddService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductAddServiceImpl implements ProductAddService {

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
    public MasterCategoryDto addMasterCategory(MasterCategoryDto masterCategoryDto) {
        MasterCategory masterCategory = modelMapper.map(masterCategoryDto, MasterCategory.class);
        masterCategory.setMasterCategoryId(UUID.randomUUID().toString());
        masterCategory.setMcBreadcrumbUrl(masterCategoryDto.getBreadcrumbUrl());
         return modelMapper.map(masterCategoryRepo.save(masterCategory), MasterCategoryDto.class);
    }

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category.setCategoryId(UUID.randomUUID().toString());
        category.setCBreadcrumbUrl(categoryRequest.getBreadcrumbUrl());
        return modelMapper.map(categoryRepo.save(category), CategoryDto.class) ;
    }

    @Override
    public SubCategoryDto addSubCategory(SubCategoryRequest subCategoryDto) {
        SubCategory subCategory = modelMapper.map(subCategoryDto, SubCategory.class);
        subCategory.setSubCategoryId(UUID.randomUUID().toString());
        subCategory.setScBreadcrumbUrl(subCategoryDto.getBreadcrumbUrl());
        return modelMapper.map(subCategoryRepo.save(subCategory),SubCategoryDto.class);
    }

    @Override
    public BrandDto addBrand(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setBrandId(UUID.randomUUID().toString());
        return modelMapper.map(brandRepo.save(brand), BrandDto.class);
    }

    @Override
    public ProductDto addProduct(ProductRequest productDto) {
        Product product=modelMapper.map(productDto, Product.class);
        product.setCreatedTimestamp(LocalDateTime.now());
        product=productRepo.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ReviewRating addReview(ReviewRating reviewRating) throws ProductException {
        ReviewRating reviewRatingResponse=new ReviewRating();
        ProductStyleVariant psv=styleVariantRepo.findById(reviewRating.getStyleId()).orElse(null);
        if( psv != null) {
            reviewRatingResponse = reviewRatingRepo.save(reviewRating);
            psv.setProductAvgRating(reviewRatingRepo.findAvgRating(psv.getStyleId()));
            psv.setReviewCount(reviewRatingRepo.findCountByStyleId(psv.getStyleId()));
            styleVariantRepo.save(psv);
            return reviewRatingResponse;
        }else {
            throw new ProductException(ErrorCode.INVALID_STYLE_ID.name());
        }
    }

    @Override
    public ProductStyleVariant addStyleVariant(StyleVariantRequest request){

        ProductStyleVariant productStyleVariant = modelMapper.map(request, ProductStyleVariant.class);
        productStyleVariant.setProduct(productRepo.findById(request.getProductId()).get());

        productStyleVariant.setProductAvgRating(reviewRatingRepo.findAvgRating(productStyleVariant.getStyleId()));
        productStyleVariant.setReviewCount(reviewRatingRepo.findCountByStyleId(productStyleVariant.getStyleId()));
        BigDecimal finalPrice = productStyleVariant.getMrp().subtract(productStyleVariant.getDiscountPercentage().multiply(productStyleVariant.getMrp()).divide(new BigDecimal(100), MathContext.DECIMAL128));
        productStyleVariant.setFinalPrice(finalPrice);

        productStyleVariant.setCreatedTimeStamp(LocalDateTime.now());
        ProductStyleVariant response= styleVariantRepo.save(productStyleVariant);

        response.getSizeDetails().forEach(svd -> svd.setSizeId(response.getStyleId()+"_"+svd.getSize()));
        return styleVariantRepo.save(response);
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse){
       return warehouseRepo.save(warehouse);
    }

    @Override
    public List<Inventory> addInventory(List<Inventory> inventory){
        inventory=inventoryRepo.saveAll(inventory);
        inventory.forEach(inv -> inventoryRepo.updateQuantity(inv.getSizeVariantId()) );
        return  inventory;
    }
}