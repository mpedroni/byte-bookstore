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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;

        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("error", status.getReasonPhrase());
        body.put("code", status.value());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("messages", List.of(ex.getMessage()));

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode _status, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "'%s' %s".formatted(error.getField(), error.getDefaultMessage()))
                .toList();

        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("error", status.getReasonPhrase());
        body.put("code", status.value());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("messages", errors);

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

        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("error", status.getReasonPhrase());
        body.put("code", status.value());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("messages", List.of(ex.getCause().getMessage()));

        return handleExceptionInternal(
                ex,
                body,
                headers,
                status,
                request
        );
    }
}
