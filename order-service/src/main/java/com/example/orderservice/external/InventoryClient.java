package com.example.orderservice.external;

import com.example.orderservice.external.models.InventoryStockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class InventoryClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<InventoryStockResponse> isInStock(List<String> skuCodes){
        InventoryStockResponse[] inventoryStockResponses = restTemplate.postForEntity("http://localhost:8050/api/inventory/stock", skuCodes,InventoryStockResponse[].class).getBody();

        List<InventoryStockResponse> inventoryStockResponseList = Arrays.asList(inventoryStockResponses);
        return inventoryStockResponseList;
    }


}
