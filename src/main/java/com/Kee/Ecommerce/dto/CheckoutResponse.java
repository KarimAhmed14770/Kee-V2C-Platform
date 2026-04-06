package com.Kee.Ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CheckoutResponse(
        Long orderId,
        BigDecimal totalPrice,
        String status,
        LocalDateTime createdAt,
        String shippingAddress

) {
}
