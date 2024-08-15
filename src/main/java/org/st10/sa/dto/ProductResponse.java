package org.st10.sa.dto;

public record ProductResponse(

            Long id,

            String name,

            Double price,

            Integer qty,

            String categoryName
) {
}
