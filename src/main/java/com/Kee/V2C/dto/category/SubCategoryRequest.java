package com.Kee.V2C.dto.category;

public record SubCategoryRequest(
                                 String name,
                                 String description,
                                 String imageUrl,
                                 boolean active) {
}
