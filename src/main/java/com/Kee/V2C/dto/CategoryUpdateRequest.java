package com.Kee.V2C.dto;

public record CategoryUpdateRequest(String name,
                                    String description,
                                    String imageUrl,
                                    boolean active) {
}
