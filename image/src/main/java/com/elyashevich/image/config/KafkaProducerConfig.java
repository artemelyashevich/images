package com.elyashevich.image.config;

import com.elyashevich.image.domain.event.NotificationEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServers;

    @Value("${application.kafka.topic:notification-topic}")
    private String topicName;

    @Value("${application.kafka.partition:1}")
    private int partitionCount;

    @Value("${application.kafka.replica:1}")
    private int replicaCount;

    @Bean
    public NewTopic orderTopic() {
        return
                TopicBuilder
                .name(this.topicName)
                .partitions(this.partitionCount)
                .replicas(this.replicaCount)
                .build();
    }

    @Bean
    public ProducerFactory<String, NotificationEvent> producerFactory() {
        var config = new HashMap<String, Object>(3);
        config.put(BOOTSTRAP_SERVERS_CONFIG, this.kafkaServers);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, NotificationEvent> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }
}