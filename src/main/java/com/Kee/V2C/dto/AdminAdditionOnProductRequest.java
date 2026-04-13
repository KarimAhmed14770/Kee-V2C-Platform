package com.Kee.V2C.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record AdminAdditionOnProductRequest(

                                            @Positive(message = "Brand ID must be a positive integer")
                                            Long brandId,
                                            @NotNull(message = "SubCategory ID is required")
                                            @Positive(message = "SubCategory ID must be a positive integer")
                                            Long subcategoryId,
                                            @NotNull
                                            String modifiedName,
                                            @NotNull
                                            String modifiedDescription,
                                            @NotNull
                                            Boolean modifiedGlobal,

                                            MultipartFile modifiedImageFile
                                            ) {
}
