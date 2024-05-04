package com.ecommerce.productservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SizeVariantDetails {

    private String skuSizeId;
    private String size;
    private String sizeDetailsImageUrl;
    private Integer quantity;
    private String availablePincodes;
}
