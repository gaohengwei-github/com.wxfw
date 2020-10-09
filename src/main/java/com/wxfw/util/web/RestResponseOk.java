package com.wxfw.util.web;


/**
 * RestResponseOk
 * 返回正确信息的工具类
 *
 * @author gaohw
 * @date 2020/3/17
 */

public class RestResponseOk implements RestResponse {

    private Object data;

    public RestResponseOk(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

