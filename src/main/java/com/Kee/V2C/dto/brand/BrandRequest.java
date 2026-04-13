package com.Kee.V2C.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequest(@NotBlank
                            @Size(min=2,max=50)
                           String name,
                           @Size(max=300) String description,
                           String imageUrl,
                           Boolean active) {
}
