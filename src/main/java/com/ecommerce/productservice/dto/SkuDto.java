package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.Images;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SkuDto {

    private String skuId;
    private String size;
    private String colour;
    private BigDecimal mrp;
    private BigDecimal discountPercentage;
    private BigDecimal finalPrice;
    private Images images;
    private Integer quantity;
    private String availablePincodes;
    private UUID productId;
}
