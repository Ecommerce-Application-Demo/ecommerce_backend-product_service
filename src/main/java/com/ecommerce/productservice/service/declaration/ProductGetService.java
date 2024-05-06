package com.ecommerce.productservice.service.declaration;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.response.*;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductGetService {

    List<MasterCategoryDto> getMasterCategory(String masterCategoryId, String masterCategoryName);

    List<CategoryDto> getCategory(String categoryName, String categoryId, String masterCategory);

    List<SubCategoryDto> getSubCategory(String subCategoryName, String subCategoryId, String categoryName);

    List<BrandDto> getBrand();

    List<ProductResponse> getProduct(String productId, String productName, String subCategoryName, String categoryName, String masterCategoryName, String brand, String gender);

    List<ReviewRating> getReview(UUID productId);

    List<StyleVariantDetailsDto> getStyleVariants(String productId, String styleId, String size, String colour );

    List<SizeInfo> getSizes(String styleId);

    Set<ColourInfo> getColours(String productId);

    Set<ProductListingResponse> getProductListing(String subCategoryName, String categoryName, String masterCategoryName, String brand, String gender);

    List<Warehouse> getWarehouse(Integer warehouseId);
}
