package org.st10.sa.dto;

public record CustomerCreateRequest(
        String telephone,

        String name,

        Double score
) {
}
