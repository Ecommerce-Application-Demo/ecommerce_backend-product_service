package com.ecommerce.productservice.entity.warehousemanagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String inventoryId;
    @NotNull
    private String sizeVariantId;
    @OneToOne
    private Warehouse warehouse;
    private int quantity;
}
