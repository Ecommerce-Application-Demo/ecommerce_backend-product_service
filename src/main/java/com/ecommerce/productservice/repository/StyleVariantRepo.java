package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.ProductStyleVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StyleVariantRepo extends JpaRepository<ProductStyleVariant, String> {


    @Query(value = "SELECT s.* FROM product.product_style_variant s " +
            "LEFT OUTER JOIN product.product p ON s.psv_product = p.product_id " +
            "LEFT OUTER JOIN product.size_details sd ON s.style_id = sd.psv_id "+
            "WHERE (?1 IS NULL OR p.product_id = CAST(?1 AS UUID)) " +
            "AND (?2 IS NULL OR sd.style_id = ?2 ) " +
            "AND (?3 IS NULL OR sd.size = ?3)"+
            "AND (?4 IS NULL OR s.colour = ?4) " +
            "GROUP BY s.style_id", nativeQuery = true)
    List<ProductStyleVariant> findStyle(String productId, String styleId, String Size, String colour);

    @Query(value = "SELECT s.* FROM product.product_style_variant s " +
            "LEFT OUTER JOIN product.size_details sd ON s.style_id = sd.psv_id "+
            "WHERE sd.size_id = ?1 ",nativeQuery = true)
    ProductStyleVariant findSize(String sizeId);

    @Modifying
    @Query(value = "DELETE FROM product.size_details sd WHERE sd.size_id = ?1",nativeQuery = true)
    void deleteSize(String sizeId);


    @Query(value = "SELECT psv.* " +
            "FROM product.product_style_variant psv " +
            "INNER JOIN product.product p ON psv.psv_product = p.product_id "+
            "WHERE to_tsvector('english', p.product_name || ' ' || p.product_category || ' ' || p.product_master_category || " +
            "COALESCE(p.product_sub_category,'') || ' ' || p.product_brand || ' ' || p.material || ' ' || p.gender || ' ' || p.product_description " +
            "|| ' ' || COALESCE(psv.colour,'') ) " +
            "@@ plainto_tsquery('english', ?1) " +
            "AND (?2 IS NULL OR psv.final_price <= ?2) " +
            "ORDER BY ?3",nativeQuery = true)
    Page<ProductStyleVariant> findProductByfield(String searchInput,Integer price,String sortBy, PageRequest pageRequest);

}
