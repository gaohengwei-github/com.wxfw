package com.wxfw.util.exception;

public class ServiceRuntimeException extends RuntimeException {

    private String message;

    private Integer errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceRuntimeException() {
    }

    public ServiceRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceRuntimeException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public ServiceRuntimeException(Integer errorCode, String message) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public ServiceRuntimeException(Integer errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }


}
