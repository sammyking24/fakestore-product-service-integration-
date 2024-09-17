package com.sammycode.store_service_production_Api.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Product {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
