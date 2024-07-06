package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.models.InventoryStockResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllBySkuCodeIn(List<String> skuCodes);
}
