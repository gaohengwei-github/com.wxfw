package com.wxfw.util.exception;

/**
 * @author gaohw
 * @create 2018-03-12 下午8:57
 **/
public class UserNotLoginException extends RuntimeException {

    public UserNotLoginException() {
        super("账号未登录");
    }
}
