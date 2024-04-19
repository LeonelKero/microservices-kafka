package com.wbt.notificationmicroservice.handler

import com.wbt.corelibrary.ProductCreatedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(topics = ["product-created-events-topic"])
class ProductCreatedEventHandler {

    private val logger = LoggerFactory.getLogger(ProductCreatedEventHandler::class.java)

    @KafkaHandler
    fun handler(productCreatedEvent: ProductCreatedEvent) {
        logger.info("***** Created product message arrived, ${productCreatedEvent.title}")
    }
}
