package com.Kee.V2C.dto;

public record SubCategoryRequest(Long parentId,
                                 String name,
                                 String description,
                                 String imageUrl,
                                 boolean active) {
}
