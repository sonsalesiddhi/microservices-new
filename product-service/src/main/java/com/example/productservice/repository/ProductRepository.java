package com.example.productservice.repository;

import com.example.productservice.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ProductRepository extends MongoRepository<Product,String> {

}
