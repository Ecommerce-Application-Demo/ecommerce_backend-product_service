package com.ecommerce.productservice.dto.request;

import com.ecommerce.productservice.entity.warehousemanagement.Warehouse;

public record InventoryReq(Warehouse warehouse, Integer quantity) {
}
