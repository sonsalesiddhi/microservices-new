package com.example.inventoryservice.controller;

import com.example.inventoryservice.models.InventoryRequest;
import com.example.inventoryservice.models.InventoryResponse;
import com.example.inventoryservice.models.InventoryStockResponse;
import com.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest){
        return inventoryService.createInventory(inventoryRequest);
    }

    @PostMapping("/stock")

    public List<InventoryStockResponse> isInStock(@RequestBody List<String> skuCodes){
        return inventoryService.isInStock(skuCodes);
    }

}
