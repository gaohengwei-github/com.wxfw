package com.wxfw.interceptor;

import com.wxfw.util.annotations.NeedPermission;
import com.wxfw.util.exception.UserUnAuthorizedException;
import com.wxfw.util.Constant.Constants;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.util.Constant.CurrentUserFactory;
import com.wxfw.util.CollectionUtils;
import com.wxfw.util.StrUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * PermissionInterceptor
 *
 * @author gaohw
 * @date 2020/3/24
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NeedPermission needPermission = handlerMethod.getMethodAnnotation(NeedPermission.class);
        // 当前请求不需要登录
        if (needPermission == null) {
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
            token = LoginInterceptor.createTempToken(StrUtils.randomUUID());
        }
        CurrentUser currentUser = CurrentUserFactory.getCurrentUser(token, Constants.TOKEN.JWT_SECURITY_KEY);
        return checkAccess(needPermission, currentUser);
    }

    protected boolean checkAccess(NeedPermission needPermission, CurrentUser currentUser) {
        String[] permissions = needPermission.permissions();
        if (permissions == null || permissions.length == 0) {
            return true;
        }
        List<String> userPermissions = currentUser.getPermissions();
        if (userPermissions.contains("*")) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(currentUser.getPermissions())) {
            for (String permission : permissions) {
                if (userPermissions.contains(permission)) {
                    return true;
                }
            }
        }
        throw new UserUnAuthorizedException();
        //return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
