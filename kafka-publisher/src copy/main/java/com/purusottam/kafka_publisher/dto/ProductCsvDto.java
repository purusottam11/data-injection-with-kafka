package com.purusottam.kafka_publisher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCsvDto {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("subcategory")
    private String subcategory;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private double price;

    @JsonProperty("discount_percentage")
    private int discountPercentage;

    @JsonProperty("final_price")
    private double finalPrice;

    @JsonProperty("average_rating")
    private double averageRating;

    @JsonProperty("review_count")
    private int reviewCount;

    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @JsonProperty("in_stock")
    private boolean inStock;

    @JsonProperty("color")
    private String color;

    @JsonProperty("size")
    private String size;

    @JsonProperty("view_count")
    private int viewCount;

    @JsonProperty("purchase_count")
    private int purchaseCount;

    @JsonProperty("wishlist_count")
    private int wishlistCount;

    @JsonProperty("is_prime_eligible")
    private boolean isPrimeEligible;

    // Convert DTO to Product model
    public com.purusottam.kafka_publisher.model.Product toProduct() {
        return new com.purusottam.kafka_publisher.model.Product(
                this.productId,
                this.productName,
                this.category,
                this.subcategory,
                this.brand,
                this.description,
                this.price,
                this.discountPercentage,
                this.finalPrice,
                this.averageRating,
                this.reviewCount,
                this.stockQuantity,
                this.inStock,
                this.color,
                this.size,
                this.viewCount,
                this.purchaseCount,
                this.wishlistCount,
                this.isPrimeEligible
        );
    }
}
