package com.Kee.V2C.dto.product;

public record LocalProductAddToStockRequest(
                                            Long subCategoryId,
                                            String name,
                                            String description,
                                            String imageUrl) {
}
