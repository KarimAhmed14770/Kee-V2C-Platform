package com.Kee.V2C.dto.product;

import com.Kee.V2C.enums.ProductModelStatus;

public record ProductModelRequest(Long subCategoryId,
                                  Long brandId,
                                  String name,
                                  String description,
                                  String imageUrl,
                                  Boolean isGlobal,
                                  Long vendorId,
                                  ProductModelStatus status) {
}
