package com.Kee.V2C.dto;

public record SubCategoryAddRequest(Long parentId,
                                    String name,
                                    String description,
                                    String imageUrl,
                                    boolean active) {
}
