package com.hyfly.milet.demo.demo_common.exception;

import lombok.Data;

@Data
public class UserCheckedException extends RuntimeException {

    public static final long serialVersionUID = 1282964945778377329L;

    private String fieldName;
    private String fieldValue;

    public UserCheckedException() {
        super();
    }

    public UserCheckedException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public UserCheckedException(String message) {
        super(message);
    }

    public UserCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCheckedException(Throwable cause) {
        super(cause);
    }

    protected UserCheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
