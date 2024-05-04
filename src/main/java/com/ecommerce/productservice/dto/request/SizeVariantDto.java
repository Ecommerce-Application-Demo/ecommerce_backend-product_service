package com.ecommerce.productservice.dto.request;

import java.util.List;

public record SizeVariantDto(String Size, List<InventoryReq> sku) {
}
