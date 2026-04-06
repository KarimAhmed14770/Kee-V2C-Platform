package com.Kee.Ecommerce.dto;

import java.time.LocalDateTime;

public record InventoryResponse(String name, String location, LocalDateTime created_at) {
}
