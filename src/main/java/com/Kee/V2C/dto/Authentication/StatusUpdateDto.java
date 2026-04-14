package com.Kee.V2C.dto.Authentication;

import com.Kee.V2C.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateDto(@NotNull UserStatus status) {
}
