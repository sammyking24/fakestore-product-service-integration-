package com.sammycode.store_service_production_Api.dtos;

import com.sammycode.store_service_production_Api.models.Product;
import lombok.Data;

@Data
public class ProductWrapper {
    private String message;
    private Product product;

    public ProductWrapper(String message, Product product) {
        this.message = message;
        this.product = product;
    }
}
