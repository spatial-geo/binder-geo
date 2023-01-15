package org.ilisi.geobinder.core.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record CurrentPositionDTO(
        @NotNull
        String idUser,
        @NotNull
        String isSession,
        @NotNull
        float lon,
        @NotNull
        float lat
) {
}
