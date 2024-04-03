package com.mpedroni.bytebookstore.localization.state;

import com.mpedroni.bytebookstore.localization.state.exceptions.StateAlreadyExists;
import com.mpedroni.bytebookstore.shared.ErrorResponseBodyBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = StateController.class)
public class StateControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(StateAlreadyExists.class)
    public ResponseEntity<?> handleStateAlreadyExists(StateAlreadyExists ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;

        var body = ErrorResponseBodyBuilder.with(status, ex, ex.getMessage());

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }
}
