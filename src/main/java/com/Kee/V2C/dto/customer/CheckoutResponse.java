package com.Kee.V2C.dto.customer;

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
