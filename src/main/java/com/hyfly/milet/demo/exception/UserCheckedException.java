package com.hyfly.milet.demo.exception;

public class UserCheckedException extends RuntimeException {

    public UserCheckedException() {
        super();
    }


    public String getFieldName() {
        return null;
    }

    public String getFieldValue() {
        return null;
    }

    @Override
    public String toString() {
        return getFieldName() + ": invalid value " + getFieldValue();
    }
}
