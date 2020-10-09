package com.wxfw.util.annotations;

import java.lang.annotation.*;

/**
 * @author gaohw
 * @version V1.0
 * @Title:
 * @Description: 标示方法是否需要登录后访问
 * @date 2016年1月27日 上午8:25:43
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NeedLogin {

    String[] roles() default {};

}
