package com.Kee.V2C.dto;

import java.time.LocalDateTime;

public record InventoryResponse(String name, String location, LocalDateTime created_at) {
}
