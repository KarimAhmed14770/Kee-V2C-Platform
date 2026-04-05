package com.Kee.Ecommerce.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price ,
        Integer stock,
        Long inventoryId,
        String imageUrl,
        Boolean status,
        Long categoryId
) {}
