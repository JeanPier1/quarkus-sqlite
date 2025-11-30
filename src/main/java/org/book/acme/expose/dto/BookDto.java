package org.book.acme.expose.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class BookDto {

    @Setter
    @Getter
    public static class Request {
        private String title;
        private String author;
        private Integer amount;

        // Auditoría
        private Boolean isActive;
        private String createdBy;
        private String lastModifiedBy;
    }

    @Getter
    @Setter
    @RegisterForReflection
    public static class Response{
        private UUID id;
        private String title;
        private String author;
        private Integer amount;
        private boolean isActive;
    }



}
