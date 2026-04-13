package com.Kee.V2C.dto.product;

import com.Kee.V2C.enums.ProductModelStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record ProductModelUpdateRequest(
                                        @Positive(message = "SubCategory ID must be a positive integer")
                                        Long subCategoryId,

                                        @Positive(message = "Brand ID must be a positive integer")
                                        Long brandId,

                                        @Size(min = 2, max = 100)
                                        String name,
                                        @Size(max = 500)
                                        String description,

                                        MultipartFile image,

                                        Boolean isGlobal,
                                        @Positive(message = "SubCategory ID must be a positive integer")
                                        Long vendorId,
                                        ProductModelStatus status) {
}
