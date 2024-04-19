package com.wbt.productms.config;

import com.wbt.productms.util.Utils;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createTopic() {
        return TopicBuilder
                .name(Utils.PRODUCT_CREATED_EVENTS_TOPIC)
                .partitions(3)
                .replicas(3) // correspond to the number of brokers
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

}
