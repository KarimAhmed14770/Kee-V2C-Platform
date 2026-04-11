package com.Kee.V2C.dto;

import com.Kee.V2C.enums.ProductRequestStatus;

public record ProductRequestResponse(Long id,
                                     String name,
                                     String description,
                                     String imageUrl,
                                     Boolean isGlobal,
                                     ProductRequestStatus status) {
}
