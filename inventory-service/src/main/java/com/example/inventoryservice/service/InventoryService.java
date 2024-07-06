package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.models.InventoryRequest;
import com.example.inventoryservice.models.InventoryResponse;
import com.example.inventoryservice.models.InventoryStockResponse;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        Inventory savedInventory =inventoryRepository.save(inventory);

        InventoryResponse inventoryResponse = InventoryResponse.builder()
                .id(savedInventory.getId())
                .skuCode(savedInventory.getSkuCode())
                .quantity(savedInventory.getQuantity())
                .build();

        return inventoryResponse;
    }

    @SneakyThrows
    public List<InventoryStockResponse> isInStock(List<String> skuCodes)  {
        log.info("wait started");
        Thread.sleep(10000);

        log.info("wait ended");

           List<Inventory> inventoryStockList = inventoryRepository.findAllBySkuCodeIn(skuCodes);

           List<InventoryStockResponse> stockResponseList = new ArrayList<>();

           for(Inventory stock: inventoryStockList){
               boolean isInStock= stock.getQuantity()>0;
               InventoryStockResponse inventoryStockResponse = InventoryStockResponse.builder()
                       .skuCode(stock.getSkuCode())
                       .isInStock(isInStock)
                       .build();

               stockResponseList.add(inventoryStockResponse);
           }
           return  stockResponseList;

    }
}
