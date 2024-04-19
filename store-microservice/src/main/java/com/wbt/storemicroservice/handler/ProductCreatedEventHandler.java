package com.wbt.storemicroservice.handler;

import com.wbt.corelibrary.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {"product-created-events-topic"})
public class ProductCreatedEventHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductCreatedEventHandler.class);

    @KafkaHandler
    public void handler(final ProductCreatedEvent productCreatedEvent) {
        LOGGER.info(">>>> New product created " + productCreatedEvent);
    }

}
