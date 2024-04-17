package com.ecommerce.productservice.dto.request;

import com.ecommerce.productservice.dto.BrandDto;
import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.MasterCategoryDto;
import com.ecommerce.productservice.dto.SubCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private UUID productId;
    private String productName;
    private String productDescription;
    private String productAvgRating;
    private String gender;
    private String material;
    private MasterCategoryDto masterCategory;
    private CategoryDto category;
    private SubCategoryDto SubCategory;
    private BrandDto brand;
}
