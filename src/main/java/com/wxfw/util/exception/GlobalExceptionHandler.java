package com.wxfw.util.exception;

import com.wxfw.util.web.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author gaohw
 * @create 2018-04-02 下午10:02
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotLoginException.class)
    public RestResponse loginExceptionHandler(UserNotLoginException ex) {
        return RestResponse.error("user not login");
    }


    @ExceptionHandler(value = UserUnAuthorizedException.class)
    public RestResponse authExceptionHandler(UserUnAuthorizedException ex) {
        return RestResponse.error("user UnAuthorized");
    }


    @ExceptionHandler(value = ServiceRuntimeException.class)
    public RestResponse serviceExceptionHandler(ServiceRuntimeException ex) {
        return RestResponse.error(401, ex.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestResponse exception(Exception ex){
//        return RestResponse.error(ex.getMessage());
//    }
}
