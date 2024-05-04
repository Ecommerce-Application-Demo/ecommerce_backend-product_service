package com.ecommerce.productservice.dto.response;

import java.util.List;

public record AvailableColours(List<ColourInfo> inStockColours, List<ColourInfo> outStockColours){}
