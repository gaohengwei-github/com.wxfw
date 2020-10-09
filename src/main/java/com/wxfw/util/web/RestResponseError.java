package com.wxfw.util.web;


import java.util.HashMap;

/**
 * RestResponseError
 * 返回错误的工具类
 *
 * @author gaohw
 * @date 2020/3/17
 */
public class RestResponseError extends HashMap<String, Object> implements RestResponse {

    public RestResponseError(String message) {
        this.put("error_code", null);
        this.put("error_message", message);
    }


    public RestResponseError(Integer status, String message) {
        this.put("error_code", status);
        this.put("error_message", message);
    }

    public Integer getErrorCode() {
        return (Integer) this.get("error_code");
    }

    public String getErrorMessage() {
        return (String) this.get("error_message");
    }



    @Override
    public Object getData() {
        return this;
    }


}

