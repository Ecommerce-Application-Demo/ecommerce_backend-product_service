package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.dto.request.SizeVariantDto;
import com.ecommerce.productservice.entity.Images;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StyleVariantDetailsDto {

    private String styleId;
    private String styleName;
    private String colour;
    private BigDecimal mrp;
    private BigDecimal discountPercentage;
    private BigDecimal finalPrice;
    private Images images;
    private List<SizeVariantDto> sizeDetails;
    private UUID productId;
}