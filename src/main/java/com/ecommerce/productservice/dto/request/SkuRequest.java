package com.ecommerce.productservice.dto.request;

import com.ecommerce.productservice.entity.Images;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SkuRequest {

    private String skuId;
    private String size;
    private String colour;
    private BigDecimal mrp;
    private BigDecimal discountPercentage;
    private Images images;
    private Integer quantity;
    private String availablePincodes;
    private UUID productId;
}