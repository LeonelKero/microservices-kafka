package com.wbt.corelibrary;

import java.math.BigDecimal;

public record ProductCreatedEvent(String id, String title, BigDecimal price, Integer quantity) {
}
