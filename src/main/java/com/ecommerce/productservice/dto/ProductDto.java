package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private UUID productId;
    private String productName;
    private String productDescription;
    private Float productAvgRating;
    private Long reviewCount;
    private String gender;
    private String material;
    private String breadcrumbUrl;
    private MasterCategoryDto masterCategory;
    private CategoryDto category;
    private SubCategoryDto SubCategory;
    private BrandDto brand;
}
