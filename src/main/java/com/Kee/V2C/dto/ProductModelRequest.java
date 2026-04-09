package com.Kee.V2C.dto;

public record ProductModelRequest(Long subCategoryId,
                                  Long brandId,
                                  String name,
                                  String description,
                                  String imageUrl,
                                  Boolean isGlobal,
                                  Long vendorId) {
}
