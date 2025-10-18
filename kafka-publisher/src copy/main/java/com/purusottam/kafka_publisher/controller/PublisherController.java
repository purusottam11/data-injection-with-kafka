package com.purusottam.kafka_publisher.controller;

import com.purusottam.kafka_publisher.model.Product;
import com.purusottam.kafka_publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishProduct(@RequestBody Product product) {
        publisherService.publishProduct(product);
        return ResponseEntity.ok("Product published successfully to Kafka!");
    }

}
