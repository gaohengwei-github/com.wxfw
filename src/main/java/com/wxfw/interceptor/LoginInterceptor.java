package com.wxfw.interceptor;

import com.wxfw.util.annotations.NeedLogin;
import com.wxfw.util.exception.UserNotLoginException;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.Constant.Constants;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.util.Constant.CurrentUserFactory;
import com.wxfw.util.CollectionUtils;
import com.wxfw.util.JsonUtils;
import com.wxfw.util.JwtUtils;
import com.wxfw.util.StrUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author gaohw
 * @version V1.0
 * @Title:
 * @Description: 登录拦截器, 如果被请求的方法上有@NeedLogin注解,那么就会做登录验证
 * @date 2016年1月27日 上午8:48:52
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedLogin loginAnno = handlerMethod.getMethodAnnotation(NeedLogin.class);
        // 当前请求不需要登录
        if (loginAnno == null) {
            return true;
        }
        Boolean hasAuth = false;
        String token = request.getHeader(Constants.TOKEN.HEADER_KEY_NAME);
        if(!StrUtils.isBlank(token)&& token.length()>7){
            String headStr = token.substring(0, 6).toLowerCase();
            if(headStr.compareTo("bearer")==0){
                token = token.substring(6,token.length());
                hasAuth = true;
            }
        }
        if(!hasAuth){
            token = createTempToken(StrUtils.randomUUID());
        }
        CurrentUser currentUser = CurrentUserFactory.getCurrentUser(token, Constants.TOKEN.JWT_SECURITY_KEY);
        Applications.setCurrentUser(currentUser);
        // 如果当前用户已经登录
        if (currentUser.isLoggedIn()) {
            return this.checkAccess(loginAnno, currentUser);
        } else {
            throw new UserNotLoginException();
        }
    }

    protected boolean checkAccess(NeedLogin loginAnno, CurrentUser currentUser) {
        String[] roles = loginAnno.roles();
        if (roles == null || roles.length == 0) {
            return true;
        }
        List<String> userRoles = currentUser.getRoles();
        if (CollectionUtils.isNotEmpty(currentUser.getRoles())) {
            for (String role : roles) {
                if (userRoles.contains(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Applications.clearCurrentUser();
    }

    protected static String createTempToken(String uid) {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccount("tempUser");
        currentUser.setLoginTime(new Date());
        currentUser.setPeriod(60 * 60 * 24 * 7);
        currentUser.setType(CurrentUser.TYPE.TMP_USER);
        currentUser.setUid(uid);
        // 创建临时的token作为用户唯一凭证
        String tempToken = JwtUtils.create(JsonUtils.toJsonString(currentUser), Constants.TOKEN.JWT_SECURITY_KEY);
        return tempToken;
    }

}
