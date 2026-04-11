package com.Kee.V2C.dto.product;

public record NewProductRequest(String name,
                                String description,
                                String imageUrl,
                                Boolean isGlobal) {}
