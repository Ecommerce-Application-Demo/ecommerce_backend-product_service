package com.ecommerce.productservice.service.declaration;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.response.AvailableColours;
import com.ecommerce.productservice.dto.response.SizeInfo;
import com.ecommerce.productservice.entity.ReviewRating;

import java.util.List;
import java.util.UUID;

public interface ProductGetService {

    List<MasterCategoryDto> getMasterCategory(String masterCategoryId, String masterCategoryName);

    List<CategoryDto> getCategory(String categoryName, String categoryId, String masterCategory);

    List<SubCategoryDto> getSubCategory(String subCategoryName, String subCategoryId, String categoryName);

    List<BrandDto> getBrand();

    List<ProductResponse> getProduct(String productId, String productName, String subCategoryName, String categoryName, String masterCategoryName, String brand, String gender);

    List<ReviewRating> getReview(UUID productId);

    List<SkuDto> getSku(String productId, String skuId, String size, String colour );

    List<SizeInfo> getSizes(String skuId);

    AvailableColours getColours(String productId);

    List<ProductResponse> getProductListing(String productId, String productName, String subCategoryName, String categoryName, String masterCategoryName, String brand, String gender);
}