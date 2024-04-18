package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.order.create.CreateOrderController;
import com.mpedroni.bytebookstore.order.find.FindOrderController;
import com.mpedroni.bytebookstore.shared.ErrorResponseBodyBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice(assignableTypes = { CreateOrderController.class, FindOrderController.class })
public class OrderControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handle(IllegalArgumentException ex) {
        var status = HttpStatus.BAD_REQUEST;
        var body = ErrorResponseBodyBuilder.with(status, ex, ex.getMessage());

        return ResponseEntity.status(status).headers(new HttpHeaders()).body(body);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        var body = ErrorResponseBodyBuilder.with(status, ex, ex.getMessage());

        return ResponseEntity.status(status).headers(new HttpHeaders()).body(body);
    }
}
