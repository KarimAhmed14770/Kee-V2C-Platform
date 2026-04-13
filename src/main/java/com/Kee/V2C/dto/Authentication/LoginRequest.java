package com.Kee.V2C.dto.Authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "user name is required")
                            String userName,
                            @NotBlank(message = "password is required")
                            String password) { }
