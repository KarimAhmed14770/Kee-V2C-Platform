package com.Kee.Ecommerce.dto;

public record SubCategoryUpdateRequest(Long parentID,
                                       String name,
                                       String description,
                                       String imageUrl,
                                       boolean active) {
}
