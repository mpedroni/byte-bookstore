package com.mpedroni.bytebookstore.shared;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode _status, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "'%s' %s".formatted(error.getField(), error.getDefaultMessage()))
                .toList();

        var body = ErrorResponseBodyBuilder.with(status, ex, errors);

        return handleExceptionInternal(
                ex,
                body,
                headers,
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode _status, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var body = ErrorResponseBodyBuilder.with(status, ex, List.of(ex.getMessage()));

        return handleExceptionInternal(
                ex,
                body,
                headers,
                status,
                request
        );
    }
}
