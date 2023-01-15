package org.ilisi.geobinder.core.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record PointDTO(
        @NotNull
        float lon,
        @NotNull
        float lat,
        @NotNull
        String idUser) {

}
