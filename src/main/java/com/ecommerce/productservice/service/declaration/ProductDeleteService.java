package com.ecommerce.productservice.service.declaration;

import java.util.UUID;

public interface ProductDeleteService {

    void deleteSize(String sizeId);

    void deleteStyle(String styleId);

    void deleteProduct(UUID productId);
}
