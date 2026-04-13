package com.Kee.V2C.dto.category;

public record SubCategoryUpdateRequest(String name,
                                       String description,
                                       String imageUrl,
                                       boolean active) {
}
