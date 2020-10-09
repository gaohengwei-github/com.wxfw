package com.wxfw.util.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * ErrorRequestException
 *
 * @author gaohw
 * @date 2020/4/18
 */
public class ErrorRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public ErrorRequestException(String msg) {
        super(msg);
    }

    public ErrorRequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }
}
