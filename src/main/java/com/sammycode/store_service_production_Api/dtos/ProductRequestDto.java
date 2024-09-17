package com.sammycode.store_service_production_Api.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductRequestDto {
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
}
