package br.com.amarques.cookpedia.dto.view;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorView(LocalDateTime timestamp,
                        Integer status,
                        String error,
                        String message,
                        String path) {

    public ErrorView(HttpStatus httpStatus, String message, String path) {
        this(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), message, path);
    }
}
