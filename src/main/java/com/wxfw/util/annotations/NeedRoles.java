package com.wxfw.util.annotations;

/**
 * 该方法是否需要某角色
 */
@NeedLogin
public @interface NeedRoles {

    String[] roles() default {};

}
