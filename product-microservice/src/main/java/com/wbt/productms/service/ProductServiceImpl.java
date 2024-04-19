package com.wbt.productms.service;

import com.wbt.corelibrary.ProductCreatedEvent;
import com.wbt.productms.rest.CreateProductRestModel;
import com.wbt.productms.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String create(CreateProductRestModel restModel) {
        final var productId = UUID.randomUUID().toString();
        // Todo 1: persist data to database before publishing event
        // create event model
        final var createdProductEvent = new ProductCreatedEvent(productId, restModel.title(), restModel.price(), restModel.quantity());
        // publish event
        final var futureResponse = this.kafkaTemplate.send(Utils.PRODUCT_CREATED_EVENTS_TOPIC, productId, createdProductEvent);
        futureResponse.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                // in case an error occurred
                LOGGER.error("====> Error sending message " + throwable.getLocalizedMessage());
            } else {
                // everything completed successfully
                LOGGER.info("====> Message successfully sent! " + sendResult.getRecordMetadata());
            }
        });

        LOGGER.info("====> Returning product ID: ".concat(productId));
        return productId;
    }
}
