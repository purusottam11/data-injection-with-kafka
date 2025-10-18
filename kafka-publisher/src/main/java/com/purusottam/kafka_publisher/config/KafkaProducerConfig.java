package com.purusottam.kafka_publisher.config;

import com.purusottam.kafka_publisher.util.AppConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {


    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConstants.BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.BATCH_SIZE_CONFIG, AppConstants.KAFKA_BATCH_SIZE_BYTES,          // 2 MB batch
                ProducerConfig.LINGER_MS_CONFIG, AppConstants.KAFKA_LINGER_MS,                  // 5 seconds
                ProducerConfig.COMPRESSION_TYPE_CONFIG, AppConstants.KAFKA_COMPRESSION_TYPE,    // compress messages
                "security.protocol", "SASL_SSL",
                "sasl.mechanism", "PLAIN",
                "sasl.jaas.config", String.format(
                        "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";",
                        AppConstants.API_KEY, AppConstants.API_SECRET
                )
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
