package com.purusottam.kafka_publisher.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    private String productId;
    private String productName;
    private String category;
    private String subcategory;
    private String brand;
    private String description;
    private double price;
    private int discountPercentage;
    private double finalPrice;
    private double averageRating;
    private int reviewCount;
    private int stockQuantity;
    private boolean inStock;
    private String color;
    private String size;
    private int viewCount;
    private int purchaseCount;
    private int wishlistCount;
    private boolean isPrimeEligible;


}
