package com.purusottam.kafka_publisher.dto;

public record ProductDto(
        String productId,
        String productName,
        String category,
        String subcategory,
        String brand,
        String description,
        double price,
        int discountPercentage,
        double finalPrice,
        double averageRating,
        int reviewCount,
        int stockQuantity,
        boolean inStock,
        String color,
        String size,
        int viewCount,
        int purchaseCount,
        int wishlistCount,
        boolean isPrimeEligible
) {
}
