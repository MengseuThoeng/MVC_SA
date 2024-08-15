package org.st10.sa.dto;

public record CustomerResponse(
        Long id,

        String telephone,

        String name,

        Double score
) {
}
