package com.example.productservice.service;

import com.example.productservice.document.Product;
import com.example.productservice.models.ProductRequest;
import com.example.productservice.models.ProductResponse;
import com.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse = ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .build();

        return productResponse;
    }

    public List<ProductResponse> getAllProducts() {
       List<Product> productList = productRepository.findAll();

       List<ProductResponse> allProduct = new ArrayList<>();
       for(Product product: productList){
           ProductResponse productResponse = ProductResponse.builder()
                   .id(product.getId())
                   .name(product.getName())
                   .description(product.getDescription())
                   .price(product.getPrice())
                   .build();
           allProduct.add(productResponse);
       }

    return  allProduct;
    }
}
