package com.sammycode.store_service_production_Api.dtos;

import com.sammycode.store_service_production_Api.models.Rating;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    private Rating rating;
}
