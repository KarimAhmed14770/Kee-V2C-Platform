package com.Kee.V2C.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        List<CartItemResponse> cartItems,
        BigDecimal totalPrice
) {

}
