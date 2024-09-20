package com.changejar.Items.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApiControllerException extends RuntimeException {
    private final String errorCode;

    public ApiControllerException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
