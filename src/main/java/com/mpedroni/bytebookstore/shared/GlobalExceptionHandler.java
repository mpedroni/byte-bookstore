package com.mpedroni.bytebookstore.shared;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;

        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> "'%s' %s".formatted(error.getField(), error.getDefaultMessage()))
                .toList();

        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        body.put("error", status.value());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("errors", errors);

        return handleExceptionInternal(
                ex,
                body,
                headers,
                status,
                request
        );

    }
}
