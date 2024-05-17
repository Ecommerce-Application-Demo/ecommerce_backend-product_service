package com.ecommerce.productservice.dto.response;

import com.ecommerce.productservice.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String productId;
    private String productName;
    private String productDescription;
    private String productAvgRating;
    private String reviewCount;
    private String gender;
    private String material;
    private String breadcrumbUrl;
    private MasterCategoryDto masterCategory;
    private CategoryDto category;
    private SubCategoryDto SubCategory;
    private BrandDto brand;
    private List<StyleVariantDetailsDto> styleVariants;
}
