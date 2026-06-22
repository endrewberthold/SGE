package org.sge.exception;

import java.time.LocalDateTime;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
