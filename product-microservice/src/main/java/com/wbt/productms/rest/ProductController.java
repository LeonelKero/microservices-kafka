package com.wbt.productms.rest;

import com.wbt.productms.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/products"})
public record ProductController(ProductService productService) {

    @PostMapping
    public ResponseEntity<String> create(final @RequestBody CreateProductRestModel productRestModel) {
        final var productId = this.productService.create(productRestModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productId);
    }

}
