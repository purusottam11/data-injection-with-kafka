package com.purusottam.kafka_publisher.service;

import com.purusottam.kafka_publisher.model.Product;
import com.purusottam.kafka_publisher.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Kafka topic name

    public void publishProduct(Product product) {
        kafkaTemplate.send(AppConstants.KAFKA_TOPIC_PRODUCTS, product.getProductId(), product);
        System.out.println("Published product to Kafka: " + product);
    }

    public void publishProductsBatch(List<Product> products) {
        for (Product product : products) {
            kafkaTemplate.send(AppConstants.KAFKA_TOPIC_PRODUCTS, product.getProductId(), product);
        }
        System.out.println("Published batch of " + products.size() + " products to Kafka.");
    }
}
