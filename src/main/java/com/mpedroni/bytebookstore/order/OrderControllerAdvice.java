package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.order.create.CreateOrderController;
import com.mpedroni.bytebookstore.order.find.FindOrderController;
import com.mpedroni.bytebookstore.shared.ErrorResponseBodyBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(assignableTypes = { CreateOrderController.class, FindOrderController.class })
public class OrderControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handle(IllegalArgumentException ex, WebRequest request, HttpStatus status) {
        var body = ErrorResponseBodyBuilder.with(status, ex, ex.getMessage());

        return ResponseEntity.status(status).headers(new HttpHeaders()).body(body);
    }
}
