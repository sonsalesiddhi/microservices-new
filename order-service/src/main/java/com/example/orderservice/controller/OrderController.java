package com.example.orderservice.controller;

import com.example.orderservice.config.models.OrderLineItemResponse;
import com.example.orderservice.config.models.OrderRequest;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<List<OrderLineItemResponse>> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));


    }
    public CompletableFuture<List<OrderLineItemResponse>> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> List.of(OrderLineItemResponse.builder().build()));

    }

}
