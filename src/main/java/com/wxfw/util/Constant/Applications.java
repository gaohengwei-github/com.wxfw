package com.wxfw.util.Constant;

import java.util.Map;

/**
 * Applications
 *
 * @author gaohw
 * @date 2020/3/19
 */
public class Applications {
    public static ThreadLocal<CurrentUser> currentUserThreadLocal = new ThreadLocal<>();
    private static Configuration configuration;

    public Applications() {
        configuration = Configuration.getInstance();
    }

    public static void putConfigurations(Map<String, Object> configs) {
        configuration.put(configs);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static CurrentUser getCurrentUser() {
        return currentUserThreadLocal.get();
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        currentUserThreadLocal.set(currentUser);
    }

    public static void clearCurrentUser() {
        currentUserThreadLocal.remove();
    }
}
