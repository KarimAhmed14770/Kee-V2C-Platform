package com.Kee.Ecommerce.dto;

public record CategoryResponse(Long id,
                               String name,
                               String description,
                               String imageUrl,
                               boolean active) {
}
