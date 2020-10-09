package com.wxfw.interceptor;

import com.wxfw.util.web.CookieHolder;
import com.wxfw.util.web.PageContext;
import com.wxfw.util.web.RequestHolder;
import com.wxfw.util.web.ResponseHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认拦截器,初始化参数
 *
 * @author gaohw
 */
public class DefaultInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestHolder.setHttpServletRequest(request);
        ResponseHolder.setHttpServletResponse(response);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                CookieHolder.put(cookie.getName(), cookie);
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        RequestHolder.clear();
        ResponseHolder.clear();
        CookieHolder.clear();
        PageContext.clear();
    }

}
