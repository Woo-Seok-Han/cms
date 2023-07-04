package com.waffle.user.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionResponse> customRequestException(final CustomException e) {
        log.warn("api Exception : {}", e.getErrorCode());
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getErrorCode()));
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {

        private String message;
        private ErrorCode errorCode;
    }

}
