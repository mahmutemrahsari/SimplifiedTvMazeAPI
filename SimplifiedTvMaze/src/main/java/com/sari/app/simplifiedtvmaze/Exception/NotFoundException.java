package com.sari.app.simplifiedtvmaze.Exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getErrorAttributes() {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("name", "Not Found");
        errorAttributes.put("message", getMessage());
        errorAttributes.put("code", httpStatus.value());
        errorAttributes.put("status", httpStatus.getReasonPhrase());
        return errorAttributes;
    }
}

