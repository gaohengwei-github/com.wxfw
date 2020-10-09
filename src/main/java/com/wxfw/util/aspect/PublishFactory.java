package com.wxfw.util.aspect;


import com.wxfw.entity.HjmallLoginLog;
import com.wxfw.service.HjmallLoginLogService;
import com.wxfw.util.AddressUtils;
import com.wxfw.util.IpUtils;
import com.wxfw.util.ServletUtils;
import com.wxfw.util.eums.*;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class PublishFactory {

//    @Autowired
//    private HjmallLoginLogService hjmallLoginLogService;
    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    public static void recordLogininfor(HjmallLoginLogService hjmallLoginLogService,final String username, final String status, final String message,
                                        final Object... args) {
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        HjmallLoginLog logininfor = new HjmallLoginLog();
        logininfor.setLoginName(username);
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(message);
        // 日志状态
        if (LoginStatus.LOGIN_SUCCESS.equals(status) || LoginStatus.LOGOUT.equals(status)) {
            logininfor.setStatus(LoginStatus.SUCCESS);
        } else if (LoginStatus.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(LoginStatus.FAIL);
        }
        // 发布事件
        hjmallLoginLogService.createLoginLog(logininfor);
    }
}
