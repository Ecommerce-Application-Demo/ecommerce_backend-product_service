package com.ecommerce.productservice.dto.request;

import lombok.Data;

@Data
public class ProductFilterReq {

    private String[] masterCategories;
    private String[] categories;
    private String[] subCategories;
    private String[] brands;
    private String[] gender;
    private String[] colours;
    private String[] sizes;
    private Integer discountPercentage;
    private Integer maxPrice;
    private Integer minPrice;
}
