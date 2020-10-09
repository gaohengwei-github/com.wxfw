package com.wxfw.util.aspect;

/**
 * OperLogAspect
 *
 * @author gaohw
 * @date 2020/4/3
 */

import com.alibaba.fastjson.JSON;
import com.wxfw.entity.HjmallOperLog;
import com.wxfw.util.eums.BusinessStatus;
import com.wxfw.util.Constant.Constants;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.util.Constant.CurrentUserFactory;
import com.wxfw.service.HjmallOperLogService;
import com.wxfw.util.AddressUtils;
import com.wxfw.util.IpUtils;
import com.wxfw.util.ServletUtils;
import com.wxfw.util.StrUtils;
import com.wxfw.util.annotations.*;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private HjmallOperLogService hjmallOperLogService;

    // 配置织入点
    @Pointcut(value = "@annotation(com.wxfw.util.annotations.OperLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            OperLog controllerLog = getAnnotationLog(joinPoint);
            if (StrUtils.isBlank(controllerLog)) {
                return;
            }
            // *========数据库日志=========*//
            HjmallOperLog operLog = new HjmallOperLog();
            operLog.setTime(System.currentTimeMillis());
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
            operLog.setBrowser(browser);
            //TODO
            String token = request.getHeader(Constants.TOKEN.HEADER_KEY_NAME);
            token = token.substring(6,token.length());
            CurrentUser currentUser = CurrentUserFactory.getCurrentUser(token, Constants.TOKEN.JWT_SECURITY_KEY);
            operLog.setOperName(currentUser.getUid());
            operLog.setDeptName(currentUser.getAccount());
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(e.getMessage());
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            //SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
            hjmallOperLogService.insertOperlog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
//            log.error("==前置通知异常==");
//            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(OperLog log, HjmallOperLog operLog, Object[] args) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog, args);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(HjmallOperLog operLog, Object[] args) throws Exception {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse))
                .collect(Collectors.toList());
        String params = JSON.toJSONString(param, true);
        operLog.setOperParam(StrUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }
}