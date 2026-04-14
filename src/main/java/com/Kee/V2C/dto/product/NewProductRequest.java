package com.Kee.V2C.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record NewProductRequest(
                                @NotBlank(message = "Product name is required")
                                @Size(min = 2, max = 100, message = "Name must be between 2 and 45 characters")
                                String name,

                                @Size(max = 300, message = "Description cannot exceed 300 characters")
                                String description,

                                @NotNull(message = "you must provide product image")
                                MultipartFile imageFile,
                                @NotNull(message = "Global status must be specified")
                                Boolean isGlobal) {}
