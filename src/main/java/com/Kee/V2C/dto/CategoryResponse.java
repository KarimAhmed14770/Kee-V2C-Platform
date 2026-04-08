package com.Kee.V2C.dto;

public record CategoryResponse(Long id,
                               String name,
                               String description,
                               String imageUrl,
                               boolean active) {
}
