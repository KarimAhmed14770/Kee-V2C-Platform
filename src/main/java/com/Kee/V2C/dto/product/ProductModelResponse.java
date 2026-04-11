package com.Kee.V2C.dto.product;

import com.Kee.V2C.enums.ProductModelStatus;

public record ProductModelResponse(Long id,
                                   Long brandId,
                                   Long subCategoryId,
                                   Long vendorId,
                                   Boolean isGlobal,
                                   String name,
                                   String description,
                                   String imageUrl,
                                   ProductModelStatus status) {
}
