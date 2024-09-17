package com.sammycode.store_service_production_Api.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Rating {
    private double rate;
    private int count;
}
