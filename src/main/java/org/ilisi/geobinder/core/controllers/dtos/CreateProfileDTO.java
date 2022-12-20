package org.ilisi.geobinder.core.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateProfileDTO(
        @NotNull
        String fullName,
        @NotNull
        String profession
) {
}
