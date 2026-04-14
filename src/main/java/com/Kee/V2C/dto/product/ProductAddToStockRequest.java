package com.Kee.V2C.dto.product;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductAddToStockRequest(
        @NotNull(message = "Model ID is required")
        @Positive(message = "Model ID must be a positive integer")
        Long modelId,

        @NotBlank(message = "Product name is required")
        @Size(min = 2, max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        @Digits(integer = 7, fraction = 2, message = "Price format is invalid (max 7 digits and 2 decimals)")
        BigDecimal price ,

        @NotNull(message = "Stock quantity is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock,
        MultipartFile imageFile,

        @NotNull(message = "Status is required")
        Boolean status
) {}
