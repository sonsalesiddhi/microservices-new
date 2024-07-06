package com.example.productservice.controller;

import com.example.productservice.models.ProductRequest;
import com.example.productservice.models.ProductResponse;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(){
         return productService.getAllProducts();

    }
}
