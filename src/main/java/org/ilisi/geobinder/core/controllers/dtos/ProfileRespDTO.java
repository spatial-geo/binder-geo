package org.ilisi.geobinder.core.controllers.dtos;

public record ProfileRespDTO(
        String idUser,
        String fullName,
        String profession,
        float radius
) {}
