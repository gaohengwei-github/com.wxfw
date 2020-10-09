package com.wxfw.util.web;

/**
 * RestResponse
 * 返回接口
 *
 * @author gaohw
 * @date 2020/3/17
 */
public interface RestResponse {

    static RestResponse ok() {
        return ok(null);
    }

    static RestResponse ok(Object data) {
        return new RestResponseOk(data);
    }

    static RestResponse error(String message) {
        return new RestResponseError(message);
    }

    static RestResponse error(Integer status, String message) {
        return new RestResponseError(status, message);
    }

    Object getData();
}