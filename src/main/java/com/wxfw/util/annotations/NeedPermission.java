package com.wxfw.util.annotations;

import java.lang.annotation.*;

/**
 * @author gaohw
 * 标记该方法是否需要权限
 * @create 2018-05-02 下午10:15
 **/
@NeedLogin
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NeedPermission {

    String[] permissions() default {};

}
