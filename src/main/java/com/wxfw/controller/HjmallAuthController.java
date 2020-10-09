package com.wxfw.controller;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wxfw.util.annotations.*;
import com.wxfw.util.aspect.PublishFactory;
import com.wxfw.entity.HjmallAdmin;
import com.wxfw.entity.HjmallPermission;
import com.wxfw.entity.HjmallRole;
import com.wxfw.util.eums.*;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.Constant.Constants;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.model.HjmallAdminLoginForm;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallAdminService;
import com.wxfw.service.HjmallLoginLogService;
import com.wxfw.service.HjmallPermissionService;
import com.wxfw.util.*;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HjmallAuthController
 *
 * @author gaohw
 * @date 2020/4/4
 */
@RestController
@Api(tags = "系统：用户认证")
public class HjmallAuthController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HjmallAdminService hjmallAdminService;
    @Autowired
    private HjmallPermissionService hjmallPermissionService;
    @Autowired
    private HjmallLoginLogService loginLogService;

    @GetMapping(value = "/login/code")
    @ApiOperation(value = "获取验证码", notes = "获取验证码", httpMethod = "GET", tags = "系统：用户认证")
    public RestResponse getCode() throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        //运算位数
        captcha.setLen(2);
        String verifyCode = captcha.getArithmeticString();
        // 获取运算的结果
        String result = captcha.text();
        String uuid = IdUtil.simpleUUID();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(111, 36, stream, verifyCode);
        redisUtil.set(uuid, result, 60);
        return RestResponse.ok(new ImgResult(captcha.toBase64(), uuid));
    }


    /**
     * 执行登录请求
     *
     * @param hjmallAdminLoginForm
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录请求处理接口", notes = "登录请求处理接口", httpMethod = "POST", tags = "系统：用户认证")
    public RestResponse userLoginInfo(@RequestBody HjmallAdminLoginForm hjmallAdminLoginForm) {
        if(!redisUtil.hasKey(hjmallAdminLoginForm.getUuid())){
            throw new ServiceRuntimeException("验证码已过期，请重新获取");
        }
        if(!redisUtil.get(hjmallAdminLoginForm.getUuid()).equals(hjmallAdminLoginForm.getCode())){
            throw new ServiceRuntimeException("验证码错误");
        }
        HjmallAdmin hjmallAdmin = hjmallAdminService.login(hjmallAdminLoginForm.getUsername(), hjmallAdminLoginForm.getPassword());
        if (hjmallAdmin == null) {
            PublishFactory.recordLogininfor(loginLogService,hjmallAdminLoginForm.getUsername(),
                    LoginStatus.LOGIN_FAIL, ResultMsg.LOGIN_ERROR);
            throw new ServiceRuntimeException("账号或密码错误");
        }
        PublishFactory.recordLogininfor(loginLogService,hjmallAdmin.getAccount(),
                LoginStatus.LOGIN_SUCCESS, ResultMsg.LOGIN_SUCCESS);
        recordLoginInfo(hjmallAdmin);
        List<HjmallRole> myRoles = hjmallAdminService.findRolesByAdminId(hjmallAdmin.getId());
        List<HjmallPermission> permissions = hjmallPermissionService.findAllPermissionByAdminId(hjmallAdmin.getId());
        CurrentUser currentUser = this.createSessionUser(hjmallAdmin, myRoles, permissions);
        String token = this.generateToken(JsonUtils.toJsonString(currentUser));
        Map<String, Object> data = new HashMap<>();
        data.put("user", currentUser);
        data.put("token", token);
        data.put("tokenPeriod", 60 * 60 * 24 * 7); // 保存7天
        return RestResponse.ok(data);
    }


    @GetMapping(value = "/info")
    @ApiOperation(value = "获取用户信息接口", notes = "登录请求处理接口", httpMethod = "GET", tags = "系统：用户认证")
    @NeedLogin
    public RestResponse getUserInfo() {
        CurrentUser currentUser = Applications.getCurrentUser();
        HjmallAdmin hjmallAdmin = hjmallAdminService.findByAccount(currentUser.getAccount());
        List<HjmallRole> myRoles = hjmallAdminService.findRolesByAdminId(hjmallAdmin.getId());
        List<HjmallPermission> permissions = hjmallPermissionService.findAllPermissionByAdminId(hjmallAdmin.getId());
        //刷新token
        CurrentUser currentUserNew = this.createSessionUser(hjmallAdmin, myRoles, permissions);
        String token = this.generateToken(JsonUtils.toJsonString(currentUserNew));
        Map<String, Object> data = new HashMap<>();
        data.put("user", currentUserNew);
        data.put("token", token);
        data.put("tokenPeriod", 60 * 60 * 24 * 7); // 保存7天
        return RestResponse.ok(data);
    }

    /**
     * 执行退出请求
     *
     * @return RestResponse
     */
    @GetMapping(value = "/logout")
    @ApiOperation(value = "执行退出请求", notes = "执行退出请求", httpMethod = "GET", tags = "系统：用户认证")
    @NeedLogin
    public RestResponse logout() {
        logout(Applications.getCurrentUser().getAccount());
        return RestResponse.ok();
    }

    private CurrentUser createSessionUser(HjmallAdmin hjmallAdmin, List<HjmallRole> roles, List<HjmallPermission> permissions) {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccount(hjmallAdmin.getAccount());
        currentUser.setLoginTime(new Date());
        currentUser.setPeriod(60 * 60 * 24 * 7);
        currentUser.setType(CurrentUser.TYPE.OPERATOR);
        currentUser.setUid(hjmallAdmin.getId());
        currentUser.setRoles(roles.stream().map(HjmallRole::getRoleName).collect(Collectors.toList()));
        currentUser.setPermissions(permissions.stream().map(HjmallPermission::getPermission).collect(Collectors.toList()));
        return currentUser;
    }

    private String generateToken(String tokenStr) {

        String jwtTokenStr = JwtUtils.create(tokenStr, Constants.TOKEN.JWT_SECURITY_KEY);
        return jwtTokenStr;
    }

    public void recordLoginInfo(HjmallAdmin user) {
        user.setLastIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLastTime(new Date());
        hjmallAdminService.updateCenter(user);
    }

    public void logout(String loginName) {
        PublishFactory.recordLogininfor(loginLogService,loginName,
                LoginStatus.LOGOUT, ResultMsg.GET_LOGOUT_SUCCESS);
    }
}
