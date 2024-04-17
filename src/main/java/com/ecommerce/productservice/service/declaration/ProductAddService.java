package com.ecommerce.productservice.service.declaration;

import com.ecommerce.productservice.dto.*;
import com.ecommerce.productservice.dto.request.CategoryRequest;
import com.ecommerce.productservice.dto.request.ProductRequest;
import com.ecommerce.productservice.dto.request.SkuRequest;
import com.ecommerce.productservice.dto.request.SubCategoryRequest;
import com.ecommerce.productservice.entity.ReviewRating;
import com.ecommerce.productservice.entity.Sku;

public interface ProductAddService {

    MasterCategoryDto addMasterCategory(MasterCategoryDto masterCategoryDto);

    CategoryDto addCategory(CategoryRequest categoryDto);

    SubCategoryDto addSubCategory(SubCategoryRequest subCategoryDto);

    BrandDto addBrand(BrandDto brandDto);

    ProductDto addProduct(ProductRequest productDto);

    ReviewRating addReview(ReviewRating reviewRating);

    Sku addSku(SkuRequest sku);
}
