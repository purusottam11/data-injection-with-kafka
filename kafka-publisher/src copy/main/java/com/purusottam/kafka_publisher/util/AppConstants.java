package com.purusottam.kafka_publisher.util;

public final class AppConstants {

    private AppConstants() {
        // private constructor to prevent instantiation
    }

    public static final String BOOTSTRAP_SERVERS = "pkc-619z3.us-east1.gcp.confluent.cloud:9092";
    public static final String API_KEY = "KOF3E5XYHYXLM7M2";
    public static final String API_SECRET = "cfltobW9j+iHQsl5IVLzF9xfHNgKL79YbeDF4OBkH9Zrp9ykikmQfgG56PqKV4Hg";

    public static final int BATCH_SIZE = 1000;
    public static final String KAFKA_TOPIC_PRODUCTS = "product";
    public static final int KAFKA_BATCH_SIZE_BYTES = 2 * 1024 * 1024; // 2 MB
    public static final int KAFKA_LINGER_MS = 5000; // 5 seconds
    public static final String KAFKA_COMPRESSION_TYPE = "snappy";
}
