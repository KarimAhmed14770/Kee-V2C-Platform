package com.Kee.V2C.dto;

public record NewProductRequest(String name,
                                String description,
                                String imageUrl,
                                Boolean isGlobal) {}
