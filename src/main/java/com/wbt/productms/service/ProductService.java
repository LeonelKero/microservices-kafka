package com.wbt.productms.service;

import com.wbt.productms.rest.CreateProductRestModel;

public interface ProductService {
    String create(final CreateProductRestModel restModel);
}
