package com.wxfw.controller;

import com.wxfw.entity.HjmallAdmin;
import com.wxfw.model.AdminPassVo;
import com.wxfw.model.HjmallAdminVo;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallAdminService;
import com.wxfw.util.ResultCode;
import com.wxfw.util.ResultMsg;
import com.wxfw.util.StrUtils;
import com.wxfw.util.web.RestResponse;
import com.wxfw.util.annotations.*;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * UserController
 *
 * @author gaohw
 * @date 2020/3/18
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/users")
public class HjmallAdminController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HjmallAdminService hjmallAdminService;

    @GetMapping
    @ApiOperation(value = "查询用户", notes = "查询用户", httpMethod = "GET", tags = "系统：用户管理")
    @NeedLogin
    @NeedPermission(permissions = {"user:list"})
    public RestResponse getUsers(@RequestParam(value = "blurry", required = false) String blurry,
                                 @RequestParam(value = "createTime", required = false) String addTime,
                                 @RequestParam(value = "page", required = false) String page,
                                 @RequestParam(value = "size", required = false) String size) {

        try {
            Object object = hjmallAdminService.findAllAdmin(blurry, addTime, Integer.valueOf(page) + 1, Integer.valueOf(size));
            return RestResponse.ok(object);
        } catch (Exception e) {
            return RestResponse.error(Integer.valueOf(ResultCode.ERROR.toString()), ResultMsg.GET_FIND_ERROR);
        }
    }

    @PostMapping
    @NeedLogin
    @ApiOperation(value = "新增管理账户", notes = "新增管理账户", httpMethod = "POST", tags = "系统：用户管理")
    @NeedPermission(permissions = {"user:add"})
    public RestResponse create(@RequestBody HjmallAdminVo hjmallAdmin) {
        if (hjmallAdminService.accountExist(hjmallAdmin.getAccount())) {
            throw new ServiceRuntimeException("该账号已经被占用");
        }
        if (hjmallAdminService.emailExist(hjmallAdmin.getEmail())) {
            throw new ServiceRuntimeException("该邮箱已经被占用");
        }
        hjmallAdminService.regist(hjmallAdmin);
        return RestResponse.ok(ResultMsg.ADD_SUCCESS);
    }

    @PutMapping
    @NeedLogin
    @ApiOperation(value = "编辑管理账户", notes = "编辑管理账户", httpMethod = "PUT", tags = "系统：用户管理")
    @NeedPermission(permissions = {"user:edit"})
    public RestResponse edit(@RequestBody HjmallAdminVo hjmallAdmin) {
        hjmallAdminService.update(hjmallAdmin);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }

    @PostMapping(value = "bindShopId")
    @NeedLogin
    @ApiOperation(value = "绑定商户ID", notes = "绑定商户ID", httpMethod = "POST", tags = "系统：用户管理")
    public RestResponse bindShopId(@RequestBody Map<String, Object> stringObjectMap) {
        hjmallAdminService.bindShopId(stringObjectMap);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }

    @PutMapping(value = "center")
    @ApiOperation(value = "修改用户：个人中心", notes = "修改用户：个人中心", httpMethod = "PUT", tags = "系统：用户管理")
    @NeedLogin
    public RestResponse center(@RequestBody HjmallAdmin hjmallAdmin) {
        if (!hjmallAdmin.getId().equals(Applications.getCurrentUser().getUid())) {
            throw new ServiceRuntimeException("只能修改自己的个人信息");
        }
        HjmallAdmin admin = hjmallAdminService.findAdminById(hjmallAdmin.getId());
        if (!StringUtils.isBlank(hjmallAdmin.getAccount())) {
            if (hjmallAdminService.accountExist(hjmallAdmin.getAccount()) && !hjmallAdmin.getAccount().equals(Applications.getCurrentUser().getAccount())) {
                throw new ServiceRuntimeException("该账号已经被占用");
            }
            admin.setAccount(hjmallAdmin.getAccount());
        }
        hjmallAdminService.updateCenter(admin);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @DeleteMapping
    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "DELETE", tags = "系统：用户管理")
    @NeedLogin
    @NeedPermission(permissions = {"user:del"})
    public RestResponse delete(@RequestBody Set<String> ids) {
        hjmallAdminService.delete(ids);
        return RestResponse.ok(ResultMsg.DEL_SUCCESS);
    }


    @PostMapping(value = "/updateAvatar")
    @ApiOperation(value = "修改头像", notes = "修改头像", httpMethod = "POST", tags = "系统：用户管理")
    @NeedLogin
    public RestResponse updateAvatar(@RequestParam MultipartFile file) throws IOException {
        hjmallAdminService.updateAvatar(file);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @PostMapping(value = "/updateEmail/{code}")
    @ApiOperation(value = "修改邮箱", notes = "修改邮箱", httpMethod = "POST", tags = "系统：用户管理")
    @NeedLogin
    public RestResponse updateEmail(@PathVariable String code, @RequestBody HjmallAdmin hjmallAdmin) {
        HjmallAdmin admin = hjmallAdminService.login(Applications.getCurrentUser().getAccount(), hjmallAdmin.getPwd());
        if (StrUtils.isBlank(admin)) {
            throw new ServiceRuntimeException("密码输入错误");
        }
        Object object = redisUtil.get("code:" + Applications.getCurrentUser().getUid());
        if (StrUtils.isBlank(object)) {
            throw new ServiceRuntimeException("验证码已经过期");
        }
        if (!code.equals(String.valueOf(object))) {
            throw new ServiceRuntimeException("验证码校验错误");
        }
        admin.setEmail(hjmallAdmin.getEmail());
        hjmallAdminService.updateCenter(admin);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @PostMapping(value = "/updatePass")
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST", tags = "系统：用户管理")
    @NeedLogin
    public RestResponse updatePass(@RequestBody AdminPassVo passVo) {
        HjmallAdmin admin = hjmallAdminService.login(Applications.getCurrentUser().getAccount(), passVo.getOldPass());
        if (passVo.getNewPass().equals(passVo.getOldPass())) {
            throw new ServiceRuntimeException("新旧密码输入一致");
        }
        if (StrUtils.isBlank(admin)) {
            throw new ServiceRuntimeException("输入的旧密码不对");
        }
        admin.setPwd(passVo.getNewPass());
        hjmallAdminService.updateCenter(admin);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);

    }
}
