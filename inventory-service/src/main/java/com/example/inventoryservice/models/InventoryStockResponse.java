package com.example.inventoryservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InventoryStockResponse {
    private String skuCode;
    private Boolean isInStock;
}
