package org.book.acme.utils;

import java.util.UUID;

public class ErrorMessages {

    private ErrorMessages() {}

    public static String entityNotFound(String entityName, UUID id){
        return String.format("%s con id %s no encontrado", entityName, id);
    }
}
