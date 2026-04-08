package com.Kee.Ecommerce.dto;

public record CategoryAddRequest(String name,
                                 String description,
                                 String imageUrl,
                                 boolean active) {
}
