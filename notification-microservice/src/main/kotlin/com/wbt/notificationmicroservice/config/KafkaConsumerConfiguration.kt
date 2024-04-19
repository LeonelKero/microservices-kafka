package com.wbt.notificationmicroservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfiguration {

    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val config: MutableMap<String, Any> = HashMap()
        environment.getProperty("spring.kafka.consumer.bootstrap-servers")
            ?.let { servers -> config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers) }
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages")
            ?.let { pkg -> config.put(JsonDeserializer.TRUSTED_PACKAGES, pkg) }
        environment.getProperty("spring.kafka.consumer.group-id")
            ?.let { config.put(ConsumerConfig.GROUP_ID_CONFIG, it) }
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenercontainerFactory(consumerFactory: ConsumerFactory<String, Any>): ConcurrentKafkaListenerContainerFactory<String, Any> {
        return ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            setConsumerFactory(consumerFactory)
        }
    }
}