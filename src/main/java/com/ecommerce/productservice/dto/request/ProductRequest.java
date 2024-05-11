package com.ecommerce.productservice.dto.request;

import com.ecommerce.productservice.dto.BrandDto;
import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.MasterCategoryDto;
import com.ecommerce.productservice.dto.SubCategoryDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private UUID productId;
    @NotNull
    private String productName;
    @NotNull
    private String productDescription;
    private String productAvgRating;
    @NotNull
    private String gender;
    @NotNull
    private String material;
    @NotNull
    private MasterCategoryDto masterCategory;
    @NotNull
    private CategoryDto category;
    private SubCategoryDto SubCategory;
    @NotNull
    private BrandDto brand;
    private String productBreadcrumbUrl;
}
