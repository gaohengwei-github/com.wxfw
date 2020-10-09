package com.wxfw.util.web;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;


/**
 * CookieHolder
 * Cookie管理器
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class CookieHolder {

    private static final ThreadLocal<Map<String, Cookie>> cookieHolder = new ThreadLocal<Map<String, Cookie>>();

    public static void put(String key, Cookie value) {
        Map<String, Cookie> cookieMap = cookieHolder.get();
        if (cookieMap == null) {
            cookieMap = new HashMap<String, Cookie>();
            cookieHolder.set(cookieMap);
        }
        cookieMap.put(key, value);
    }

    public static Cookie get(String key) {
        Map<String, Cookie> cookieMap = cookieHolder.get();
        if (cookieMap == null) {
            return null;
        }
        return cookieHolder.get().get(key);
    }

    public static String getValue(String key) {
        Cookie cookie = get(key);
        return cookie == null ? null : cookie.getValue();
    }

    public static void clear() {
        cookieHolder.remove();
    }
}
