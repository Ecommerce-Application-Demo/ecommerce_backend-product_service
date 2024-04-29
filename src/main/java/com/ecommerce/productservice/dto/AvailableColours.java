package com.ecommerce.productservice.dto;

import java.util.Map;

public record AvailableColours(Map<String,String> inStockColours,Map<String,String> outStockColours){}
