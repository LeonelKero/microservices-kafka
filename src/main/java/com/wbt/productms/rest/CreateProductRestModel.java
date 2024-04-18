package com.wbt.productms.rest;

import java.math.BigDecimal;

public record CreateProductRestModel(String title, BigDecimal price, Integer quantity) {
}
