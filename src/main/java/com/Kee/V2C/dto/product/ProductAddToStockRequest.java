package com.Kee.V2C.dto.product;

import java.math.BigDecimal;

public record ProductAddToStockRequest(
        Long modelId,
        String name,
        String description,
        BigDecimal price ,
        Integer stock,
        String imageUrl,
        Boolean status
) {}
