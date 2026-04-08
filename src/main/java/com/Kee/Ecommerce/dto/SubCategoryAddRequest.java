package com.Kee.Ecommerce.dto;

public record SubCategoryAddRequest(Long parentId,
                                    String name,
                                    String description,
                                    String imageUrl,
                                    boolean active) {
}
