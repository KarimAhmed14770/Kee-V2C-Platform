package com.Kee.V2C.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SubCategoryUpdateRequest(
                                       @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
                                       @Pattern(regexp = "^[a-zA-Z\\s&]+$", message = "Name can only contain letters, spaces, and '&'")
                                        String name,
                                       @Size(max = 500, message = "Description cannot exceed 500 characters")
                                       String description,

                                       String imageUrl,
                                       boolean active) {
}
