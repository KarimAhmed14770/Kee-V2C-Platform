package com.Kee.Ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceResponse(
        long orderId,//order id
        LocalDateTime generatedAt,
        List<OrderItemResponse> orderItems,
        BigDecimal totalPrice
) {
}
