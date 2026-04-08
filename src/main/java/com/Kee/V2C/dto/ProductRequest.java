package com.Kee.V2C.dto;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price ,
        Integer stock,
        Long shopId,
        String imageUrl,
        Boolean status,
        Long categoryId
) {}
