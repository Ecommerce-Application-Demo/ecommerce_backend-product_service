package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.Images;
import com.ecommerce.productservice.entity.SizeVariantDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SkuDto {

    private String skuId;
    private String skuName;
    private String colour;
    private BigDecimal mrp;
    private BigDecimal discountPercentage;
    private BigDecimal finalPrice;
    private Images images;
    private List<SizeVariantDetails> sizeVariantDetails;
    private UUID productId;
}
