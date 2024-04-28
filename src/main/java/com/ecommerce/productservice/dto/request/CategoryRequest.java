package com.ecommerce.productservice.dto.request;

import com.ecommerce.productservice.dto.MasterCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private UUID categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryDefaultImage;
    private MasterCategoryDto masterCategoryDto;
}
