package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.warehousemanagement.Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface InventoryRepo extends ListCrudRepository<Inventory, UUID> {

    List<Inventory> findBySizeVariantId(String sizeVariantId);

    @Procedure(procedureName = "product.update_quantity")
    void updateQuantity(String sizeid);

     @Query(value = "SELECT SUM(quantity) " +
                    "FROM product.inventory i " +
                    "WHERE i.size_variant_id = ?1",nativeQuery = true)
    Integer SumQuantityBySizeVariantId(String sizeVariantId);
}
