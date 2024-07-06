package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderLineItem;
import com.example.orderservice.event.OrderPlacedEvent;
import com.example.orderservice.external.InventoryClient;
import com.example.orderservice.external.models.InventoryStockResponse;
import com.example.orderservice.config.models.OrderLineItemResponse;
import com.example.orderservice.config.models.OrderRequest;
import com.example.orderservice.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.orderservice.config.models.OrderLineItemRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Take all items in input from user which he wants to order
// Check if all items if they are iin stock
// if all items are in stock then save order

// price
//

@Service
public class OrderService {
    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Autowired
    private ObservationRegistry observationRegistry;

    public List<OrderLineItemResponse> placeOrder(OrderRequest orderRequest) {
        List<String>skucodes = orderRequest.getOrderLineItemRequests().stream().map(oderitem ->oderitem.getSkuCode()).collect(Collectors.toList());

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        return inventoryServiceObservation.observe(() ->{
            List<InventoryStockResponse> inventoryStockResponseList = inventoryClient.isInStock(skucodes);

            boolean isInStock = inventoryStockResponseList.stream().allMatch(stock -> stock.getIsInStock()==true);

            List<OrderLineItem> orderLineItemList = new ArrayList<>();
            for(OrderLineItemRequest orderLineItemRequest : orderRequest.getOrderLineItemRequests()){
                OrderLineItem orderLineItem = OrderLineItem.builder()
                        .price(orderLineItemRequest.getPrice())
                        .skuCode(orderLineItemRequest.getSkuCode())
                        .quantity(orderLineItemRequest.getQuantity())
                        .build();
                orderLineItemList.add(orderLineItem);
            }

            if(isInStock){
                Order order = Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .orderLineItem(orderLineItemList)
                        .build();

                Order savedOrder =orderRepository.save(order);

                List<OrderLineItemResponse> orderLineItemResponseList = new ArrayList<>();

                for(OrderLineItem orderLineItem : savedOrder.getOrderLineItem()){
                    OrderLineItemResponse orderLineItemResponse = OrderLineItemResponse.builder()
                            .id(orderLineItem.getId())
                            .price(orderLineItem.getPrice())
                            .quantity(orderLineItem.getQuantity())
                            .skuCode(orderLineItem.getSkuCode())
                            .build();
                    orderLineItemResponseList.add(orderLineItemResponse);
                }
                applicationEventPublisher.publishEvent("Hi sidhi pagal");
                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, savedOrder.getOrderNumber()));
//            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(savedOrder.getOrderNumber()));

                return orderLineItemResponseList;
            }
            return List.of();

        });

    }
}


