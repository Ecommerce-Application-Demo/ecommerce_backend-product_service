package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.ProductStyleVariant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StyleVariantRepo extends CrudRepository<ProductStyleVariant, String> {


    @Query(value = "SELECT s.* FROM product.product_style_variant s " +
            "LEFT OUTER JOIN product.product p ON s.psv_product = p.product_id " +
            "LEFT OUTER JOIN product.size_details sd ON s.style_id = sd.psv_id "+
            "WHERE (?1 IS NULL OR p.product_id = CAST(?1 AS UUID)) " +
            "AND (?2 IS NULL OR s.style_id = ?2 ) " +
            "AND (?3 IS NULL OR sd.size = ?3)"+
            "AND (?4 IS NULL OR s.colour = ?4) " +
            "GROUP BY s.style_id", nativeQuery = true)
    List<ProductStyleVariant> findStyle(String productId, String styleId, String Size, String colour);

    @Query(value = "SELECT s.* FROM product.product_style_variant s " +
            "LEFT OUTER JOIN product.size_details sd ON s.style_id = sv.psv_id "+
            "WHERE sd.size_variant_id = ?1 ",nativeQuery = true)
    ProductStyleVariant findSize(String sizeId);

}
