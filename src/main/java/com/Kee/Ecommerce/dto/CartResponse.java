package com.Kee.Ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        List<CartItemResponse> cartItems,
        BigDecimal totalPrice
) {

}
