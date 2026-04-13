package com.Kee.V2C.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductRequest(
                                @NotBlank(message = "Product name is required")
                                @Size(min = 2, max = 45, message = "Name must be between 2 and 45 characters")
                                String name,

                                @Size(max = 300, message = "Description cannot exceed 300 characters")
                                String description,
                                String imageUrl,
                                @NotNull(message = "Global status must be specified")
                                Boolean isGlobal) {}
