package com.Kee.V2C.dto;

public record CategoryRequest(String name,
                              String description,
                              String imageUrl,
                              boolean active) {
}
