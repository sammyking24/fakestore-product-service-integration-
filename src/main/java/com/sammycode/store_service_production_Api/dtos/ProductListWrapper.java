package com.sammycode.store_service_production_Api.dtos;

import com.sammycode.store_service_production_Api.models.Product;
import lombok.Data;

import java.util.List;
@Data
public class ProductListWrapper {
    private String message;
    private List<Product> products;

    public ProductListWrapper(String message, List<Product> products) {
        this.message = message;
        this.products = products;
    }
}
