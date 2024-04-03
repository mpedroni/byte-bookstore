package com.mpedroni.bytebookstore.shared;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseBodyBuilder {
    public static Map<Object, Object> with(HttpStatus status, Exception ex, String message) {
        var body = withDefaultFields(status, ex);
        body.put("message", message);

        return body;
    }

    public static Map<Object, Object> with(HttpStatus status, Exception ex, Iterable<?> messages) {
        var body = withDefaultFields(status, ex);
        body.put("messages", messages);

        return body;
    }

    private static Map<Object, Object> withDefaultFields(HttpStatus status, Exception ex) {
        var body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("error", status.getReasonPhrase());
        body.put("code", status.value());
        body.put("exception", ex.getClass().getSimpleName());

        return body;
    }
}
