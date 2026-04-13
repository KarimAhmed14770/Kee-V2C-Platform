package com.Kee.V2C.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record BrandUpdateRequest(
                                 @Size(min=2,max=50)
                                 String name,
                                 @Size(max=300) String description,
                                 MultipartFile imageFile,
                                 Boolean active) {
}
