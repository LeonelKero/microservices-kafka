package com.wbt.notificationmicroservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfiguration {

    @Value(value = "\${spring.kafka.consumer.bootstrap-servers}")
    private lateinit var brokers: MutableList<String>

    @Value(value = "\${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private lateinit var trustedPackages: String

    @Value(value = "\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val config: MutableMap<String, Any> = HashMap()
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages)
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)

        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenercontainerFactory(consumerFactory: ConsumerFactory<String, Any>): ConcurrentKafkaListenerContainerFactory<String, Any> {
        return ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            setConsumerFactory(consumerFactory)
        }
    }
}