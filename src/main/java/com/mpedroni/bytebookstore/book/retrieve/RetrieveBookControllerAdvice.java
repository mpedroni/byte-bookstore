package com.mpedroni.bytebookstore.book.retrieve;

import com.mpedroni.bytebookstore.book.exception.BookNotFoundException;
import com.mpedroni.bytebookstore.shared.ErrorResponseBodyBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice(assignableTypes = RetrieveBookController.class)
public class RetrieveBookControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var body = ErrorResponseBodyBuilder.with(status, ex, ex.getMessage());

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                status,
                request
        );
    }
}
