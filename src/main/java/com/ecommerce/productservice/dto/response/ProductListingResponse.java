package com.ecommerce.productservice.dto.response;

import com.ecommerce.productservice.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListingResponse {

    private String productId;
    private String productAvgRating;
    private String reviewCount;
    private String brandName;
    private String styleId;
    private String styleName;
    private String colour;
    private String colourHexCode;
    private BigDecimal mrp;
    private BigDecimal discountPercentage;
    private String discountPercentageText;
    private BigDecimal finalPrice;
    private Images images;
    private String defaultImage;
    private boolean isNewlyAdded;
    private boolean isOnlyFewLeft;
    private boolean isInStock;
}
