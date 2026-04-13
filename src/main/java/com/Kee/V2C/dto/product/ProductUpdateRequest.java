package com.Kee.V2C.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateRequest (
                                    @Size(min = 2, max = 100)
                                    String name,
                                    @Size(max = 500)
                                    String description,
                                    String image_url,
                                    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
                                    @Digits(integer = 7, fraction = 2, message = "Price format is invalid (max 7 digits and 2 decimals)")
                                    BigDecimal price){
}
