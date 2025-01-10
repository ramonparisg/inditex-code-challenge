package com.inditex.challenge.infra.primary.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<String>> handleMissingRequestValueException(ServerWebInputException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return Mono.just(ResponseEntity.internalServerError().body("Internal Server Error"));
    }
}
