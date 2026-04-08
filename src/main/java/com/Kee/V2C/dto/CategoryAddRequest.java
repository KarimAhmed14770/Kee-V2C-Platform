package com.Kee.V2C.dto;

public record CategoryAddRequest(String name,
                                 String description,
                                 String imageUrl,
                                 boolean active) {
}
