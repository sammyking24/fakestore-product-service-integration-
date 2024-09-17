package com.sammycode.store_service_production_Api.services.impl;

import com.sammycode.store_service_production_Api.dtos.ProductRequestDto;
import com.sammycode.store_service_production_Api.dtos.ProductResponseDto;
import com.sammycode.store_service_production_Api.exceptions.NoSuchProductFoundException;
import com.sammycode.store_service_production_Api.models.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public Product getProductById(Long id) throws NoSuchProductFoundException;
    public List<Product> getProducts(Map<String,String> allParams);
    public Product addNewProduct(ProductRequestDto productRequestDto);
    public Product updateProduct(Long id, ProductRequestDto productRequestDto);

}
