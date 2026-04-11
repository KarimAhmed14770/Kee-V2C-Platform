package com.Kee.V2C.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) { }
