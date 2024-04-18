package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private UUID productId;
    private String productName;
    private String productDescription;
    private String productAvgRating;
    private String reviewCount;
    private String gender;
    private String material;
    private MasterCategoryDto masterCategory;
    private CategoryDto category;
    private SubCategoryDto SubCategory;
    private BrandDto brand;
}
