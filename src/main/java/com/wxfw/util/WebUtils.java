package com.wxfw.util;

import javax.servlet.http.HttpServletRequest;

/**
 * WebUtils
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class WebUtils {
    public static String getRemoteIP(HttpServletRequest Request) {
        if (Request.getHeader("x-forwarded-for") == null) {
            String ip = Request.getRemoteAddr();
            return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        }
        return Request.getHeader("x-forwarded-for");
    }

    public static String getWebDiskTempPath() {
        return getWebDiskPath() + "/temp";
    }

    public static String getDomain(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static String getWebStaticResourceDiskPath() {
        return getWebDiskPath() + "/static";
    }

    public static String getWebDiskPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
}
