package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.request.CategoryRequest;
import com.ecommerce.productservice.dto.request.ProductRequest;
import com.ecommerce.productservice.dto.request.SkuRequest;
import com.ecommerce.productservice.dto.request.SubCategoryRequest;
import com.ecommerce.productservice.entity.*;
import com.ecommerce.productservice.repository.*;
import com.ecommerce.productservice.service.declaration.ProductAddService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
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
    SkuRepo skuRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public MasterCategoryDto addMasterCategory(MasterCategoryDto masterCategoryDto) {
        MasterCategory masterCategory = modelMapper.map(masterCategoryDto, MasterCategory.class);
        masterCategory.setMasterCategoryId(UUID.randomUUID());
         return modelMapper.map(masterCategoryRepo.save(masterCategory), MasterCategoryDto.class);
    }

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category.setCategoryId(UUID.randomUUID());
        return modelMapper.map(categoryRepo.save(category), CategoryDto.class) ;
    }

    @Override
    public SubCategoryDto addSubCategory(SubCategoryRequest subCategoryDto) {
        SubCategory subCategory = modelMapper.map(subCategoryDto, SubCategory.class);
        subCategory.setSubCategoryId(UUID.randomUUID());
        return modelMapper.map(subCategoryRepo.save(subCategory),SubCategoryDto.class);
    }

    @Override
    public BrandDto addBrand(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setBrandId(UUID.randomUUID());
        return modelMapper.map(brandRepo.save(brand), BrandDto.class);
    }

    @Override
    public ProductDto addProduct(ProductRequest productDto) {
        Product product=modelMapper.map(productDto, Product.class);

        product.setProductAvgRating(reviewRatingRepo.findAvgRating(product.getProductId()).toString());
        product.setReviewCount(reviewRatingRepo.findCountByProductId(product.getProductId()).toString());

        product=productRepo.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ReviewRating addReview(ReviewRating reviewRating){
        ReviewRating reviewRatingResponse=new ReviewRating();
        Product product=productRepo.findById(reviewRating.getProductId()).orElse(null);
        if( product != null) {
            reviewRatingResponse = reviewRatingRepo.save(reviewRating);
            product.setProductAvgRating(reviewRatingRepo.findAvgRating(product.getProductId()).toString());
            product.setReviewCount(reviewRatingRepo.findCountByProductId(product.getProductId()).toString());
            productRepo.save(product);
        }
        return reviewRatingResponse;
    }

    @Override
    public ProductStyleVariant addSku(SkuRequest skuDto){
        ProductStyleVariant productStyleVariant = modelMapper.map(skuDto, ProductStyleVariant.class);
        productStyleVariant.setImages(skuDto.getImages());
        productStyleVariant.setProduct(productRepo.findById(skuDto.getProductId()).get());
        BigDecimal finalPrice = productStyleVariant.getMrp().subtract(productStyleVariant.getDiscountPercentage().multiply(productStyleVariant.getMrp()).divide(new BigDecimal(100), MathContext.DECIMAL128));
        productStyleVariant.setFinalPrice(finalPrice);
        ProductStyleVariant response=skuRepo.save(productStyleVariant);
        response.getSizeDetails().forEach(svd -> svd.setSkuSizeId(response.getSkuId()+"_"+svd.getSize()));
        return skuRepo.save(response);
    }
}
