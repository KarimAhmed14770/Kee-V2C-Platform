package com.Kee.V2C.dto;

public record SubCategoryResponse(Long parentID,
                                  Long id,
                                  String name,
                                  String description,
                                  String imageUrl,
                                  boolean active) {
}
