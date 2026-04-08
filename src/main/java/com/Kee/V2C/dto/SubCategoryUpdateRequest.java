package com.Kee.V2C.dto;

public record SubCategoryUpdateRequest(Long parentID,
                                       String name,
                                       String description,
                                       String imageUrl,
                                       boolean active) {
}
