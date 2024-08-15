package org.st10.sa.dto;

public record ProductCreateRequest(

        String name,

        Double price,

        Integer qty,

        String categoryName

) {
}
