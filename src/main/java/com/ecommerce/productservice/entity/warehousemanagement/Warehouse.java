package com.ecommerce.productservice.entity.warehousemanagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseId;
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePincode;
    private String warehouse_email;
    private String serviceablePincodeZones;

}
