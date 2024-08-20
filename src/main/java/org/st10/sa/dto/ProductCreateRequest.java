package org.st10.sa.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProductCreateRequest(

        String name,

        Double price,

        Integer qty,

        MultipartFile image,

        String categoryName

) {
}
