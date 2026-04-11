package com.Kee.V2C.dto;

import java.math.BigDecimal;

public record ProductAddToStockRequest(
        Long productModelId,
        Long vendorId,
        Long shopId,
        String name,
        String description,
        BigDecimal price ,
        Integer stock,
        String imageUrl,
        Boolean status
) {}
