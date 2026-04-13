package com.Kee.V2C.dto.category;

public record SubCategoryRegisterRequest(
                                 String name,
                                 String description,
                                 String imageUrl,
                                 boolean active) {
}
