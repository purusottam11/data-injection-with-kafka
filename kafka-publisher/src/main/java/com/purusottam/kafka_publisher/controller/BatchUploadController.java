package com.purusottam.kafka_publisher.controller;

import com.purusottam.kafka_publisher.model.Product;
import com.purusottam.kafka_publisher.service.PublisherService;
import com.purusottam.kafka_publisher.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class BatchUploadController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publish/batch/local")
    public ResponseEntity<String> uploadProductsFromLocal(@RequestParam("filePath") String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.badRequest().body("Invalid file path: " + filePath);
        }

        try {
            // Determine file type by extension
            if (filePath.toLowerCase().endsWith(".csv")) {
                return processCsvFile(file);
            } else if (filePath.toLowerCase().endsWith(".json")) {
                return processJsonFile(file);
            } else {
                return ResponseEntity.badRequest()
                        .body("Unsupported file format. Only CSV and JSON files are supported.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Failed to upload and publish products: " + e.getMessage());
        }
    }

    /**
     * Process CSV file and publish products in batches
     */
    private ResponseEntity<String> processCsvFile(File file) throws IOException {
        List<Product> batch = new ArrayList<>();
        int totalProducts = 0;
        int batchCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header line
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return ResponseEntity.badRequest().body("CSV file is empty");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Product product = parseCsvLine(line);
                    batch.add(product);
                    totalProducts++;

                    if (batch.size() == AppConstants.BATCH_SIZE) {
                        publisherService.publishProductsBatch(batch);
                        batchCount++;
                        System.out.println("Published batch " + batchCount +
                                " with " + batch.size() + " products");
                        batch.clear();
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("Error: " + e.getMessage());
                    // Continue processing other lines
                }
            }

            // Send remaining products
            if (!batch.isEmpty()) {
                publisherService.publishProductsBatch(batch);
                batchCount++;
                System.out.println("Published final batch " + batchCount +
                        " with " + batch.size() + " products");
            }

            return ResponseEntity.ok(String.format(
                    "CSV file processed successfully! Total products: %d, Batches: %d",
                    totalProducts, batchCount));

        }
    }

    /**
     * Process JSON file and publish products in batches
     */
    private ResponseEntity<String> processJsonFile(File file) throws IOException {
        // Keep your existing JSON processing logic here
        return ResponseEntity.ok("JSON file processed successfully!");
    }

    /**
     * Parse a single CSV line into a Product object
     * Handles fields enclosed in quotes and commas within quoted fields
     */
    private Product parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        // Add the last field
        fields.add(currentField.toString().trim());

        // Map CSV fields to Product object
        Product product = new Product();

        try {
            product.setProductId(fields.get(0));
            product.setProductName(fields.get(1));
            product.setCategory(fields.get(2));
            product.setSubcategory(fields.get(3));
            product.setBrand(fields.get(4));
            product.setDescription(fields.get(5));
            product.setPrice(parseDouble(fields.get(6)));
            product.setDiscountPercentage(parseInt(fields.get(7)));
            product.setFinalPrice(parseDouble(fields.get(8)));
            product.setAverageRating(parseDouble(fields.get(9)));
            product.setReviewCount(parseInt(fields.get(10)));
            product.setStockQuantity(parseInt(fields.get(11)));
            product.setInStock(parseBoolean(fields.get(12)));
            product.setColor(fields.get(13));
            product.setSize(fields.get(14));
            product.setViewCount(parseInt(fields.get(15)));
            product.setPurchaseCount(parseInt(fields.get(16)));
            product.setWishlistCount(parseInt(fields.get(17)));
            product.setPrimeEligible(parseBoolean(fields.get(18)));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing product fields: " + e.getMessage(), e);
        }

        return product;
    }

    // Helper methods for type conversion
    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("NA")) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("NA")) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("NA")) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
