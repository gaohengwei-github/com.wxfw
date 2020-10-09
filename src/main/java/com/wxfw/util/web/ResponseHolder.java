package com.wxfw.util.web;

import javax.servlet.http.HttpServletResponse;

/**
 * ResponseHolder
 * Response持有类
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class ResponseHolder {

    private static final ThreadLocal<HttpServletResponse> responseWrapper = new ThreadLocal<HttpServletResponse>();

    public static HttpServletResponse getHttpServletResponse() {
        return responseWrapper.get();
    }

    public static void setHttpServletResponse(HttpServletResponse response) {
        responseWrapper.set(response);
    }

    public static void clear() {
        responseWrapper.remove();
    }
}
