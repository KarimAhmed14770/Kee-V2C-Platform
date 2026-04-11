package com.Kee.V2C.dto;

import com.Kee.V2C.enums.ProductModelStatus;

public record LocalProductAddToStockRequest(Long subCategoryId,
                                            String name,
                                            String description,
                                            String imageUrl) {
}

/*
i stopped here just after finishing the addglobalproduct to stock function, that's it for 2day
 */