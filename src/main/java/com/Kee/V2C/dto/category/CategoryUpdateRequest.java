package com.Kee.V2C.dto.category;

public record CategoryUpdateRequest(String name,
                                    String description,
                                    String imageUrl,
                                    boolean active) {
}
