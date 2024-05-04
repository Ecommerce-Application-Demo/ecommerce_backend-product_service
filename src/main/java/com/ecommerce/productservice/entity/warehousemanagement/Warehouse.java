package com.ecommerce.productservice.entity.warehousemanagement;

import jakarta.persistence.Entity;
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
    private int warehouseId;
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePincode;
    private String warehouse_email;
    private String serviceablePincodeZones;

}
