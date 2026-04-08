package com.Kee.Ecommerce.dto;

public record CategoryUpdateRequest(String name,
                                    String description,
                                    String imageUrl,
                                    boolean active) {
}
