package com.wxfw.util.exception;

/**
 * @author gaohw
 * 2015年8月16日下午9:01:04
 * @Description:
 */
public class UserUnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = -3811080065290754086L;

    public UserUnAuthorizedException() {
        super("UserUnAuthorized");
    }

}
