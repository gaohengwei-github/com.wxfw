package com.wxfw.controller;

import com.wxfw.model.HjmallPermissionVo;
import com.wxfw.util.annotations.*;
import com.wxfw.entity.HjmallPermission;
import com.wxfw.util.eums.BusinessType;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallPermissionService;
import com.wxfw.util.ResultCode;
import com.wxfw.util.ResultMsg;
import com.wxfw.util.StrUtils;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * PermissionController
 *
 * @author gaohw
 * @date 2020/3/23
 */
@Api(tags = "系统：菜单权限管理")
@RestController
@RequestMapping("/menus")
public class HjmallPermissionController {

    @Autowired
    private HjmallPermissionService hjmallPermissionService;
    @Autowired
    private RedisUtil redisUtil;


    @GetMapping(value = "/build")
    @NeedLogin
    @ApiOperation(value = "刷新菜单", notes = "刷新菜单", httpMethod = "GET", tags = "系统：菜单权限管理")
    public RestResponse buildMenus() {
        CurrentUser currentUser = Applications.getCurrentUser();
        Object object = redisUtil.get("menu:" + currentUser.getUid());
        Map<String, Object> data = new HashMap<>();
        if (object != null) {
            data.put("menus", object);
            return RestResponse.ok(data);
        } else {
            List<HjmallPermission> permissions = hjmallPermissionService.findAllMenuByAdminId(currentUser.getUid());
            List<HjmallPermissionVo> buildMenus = hjmallPermissionService.buildMenus(permissions);
            if (buildMenus != null) {
                data.put("menus", buildMenus);
                return RestResponse.ok(data);
            } else {
                return RestResponse.error(Integer.valueOf(ResultCode.ERROR.toString()), ResultMsg.GET_FIND_ERROR);
            }
        }
    }


    @PostMapping
    @NeedLogin
    @ApiOperation(value = "增加菜单", notes = "增加菜单", httpMethod = "POST", tags = "系统：菜单权限管理")
    @NeedPermission(permissions = {"menu:add"})
    @OperLog(title = "增加菜单", businessType = BusinessType.INSERT)
    public RestResponse add(@Validated @RequestBody HjmallPermission hjmallPermission) {
        //菜单名字和组件名称唯一
        if (hjmallPermissionService.exsitName(hjmallPermission.getName())) {
            throw new ServiceRuntimeException("该菜单已存在");
        }
        hjmallPermissionService.create(hjmallPermission);
        return RestResponse.ok(ResultMsg.ADD_SUCCESS);
    }


    @PutMapping
    @NeedLogin
    @ApiOperation(value = "修改菜单", notes = "修改菜单", httpMethod = "PUT", tags = "系统：菜单权限管理")
    @NeedPermission(permissions = {"menu:edit"})
    @OperLog(title = "修改菜单", businessType = BusinessType.UPDATE)
    public RestResponse update(@Validated @RequestBody HjmallPermission hjmallPermission) {
        if (hjmallPermission.getId().equals(hjmallPermission.getPid())) {
            throw new ServiceRuntimeException("自己不能自己的父级菜单");
        }
        HjmallPermission hjmallPermission1 = hjmallPermissionService.findPermissionById(hjmallPermission.getId());
        //菜单名字和组件名称唯一
        if (hjmallPermissionService.exsitName(hjmallPermission.getName())
                && !hjmallPermission.getName().equals(hjmallPermission1.getName())) {
            throw new ServiceRuntimeException("该菜单已存在");
        }
        hjmallPermission.setAddTime(hjmallPermission1.getAddTime());
        hjmallPermissionService.update(hjmallPermission);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @GetMapping(value = "/tree")
    @NeedLogin
    @ApiOperation(value = "构建权限菜单树接口", notes = "构建权限菜单树接口", httpMethod = "GET", tags = "系统：菜单权限管理")
    @NeedPermission(permissions = {"menu:list"})
    public RestResponse getMenuTree() {
        CurrentUser currentUser = Applications.getCurrentUser();
        Object object = redisUtil.get("tree:" + currentUser.getUid());
        if (StrUtils.isBlank(object)) {
            //菜单树  不隐藏 不禁用
            object = hjmallPermissionService.getMenuTree(hjmallPermissionService.findByPid("0"));
        }
        return RestResponse.ok(object);
    }


    @GetMapping
    @NeedLogin
    @ApiOperation(value = "获取列表接口", notes = "获取列表接口", httpMethod = "GET", tags = "系统：菜单权限管理")
    @NeedPermission(permissions = {"menu:list"})
    public RestResponse getMenus(@RequestParam(value = "blurry", required = false) String name,
                                 @RequestParam(value = "createTime", required = false) String addTime) {
        Map<String, Object> hjmallPermissionList = hjmallPermissionService.findAllPermissionList(name, addTime);
        return RestResponse.ok(hjmallPermissionList);
    }


    @DeleteMapping
    @NeedPermission(permissions = {"menu:del"})
    @ApiOperation(value = "删除菜单", notes = "删除菜单(修改菜单状态逻辑删除)", httpMethod = "DELETE", tags = "系统：菜单权限管理")
    @OperLog(title = "删除菜单", businessType = BusinessType.DELETE)
    public RestResponse delete(@RequestBody Set<String> ids) {
        Set<HjmallPermission> menuSet = new HashSet<>();
        for (String id : ids) {
            List<HjmallPermission> children = hjmallPermissionService.findByPid(id);
            if (children != null && !children.isEmpty()) {
                throw new ServiceRuntimeException("该菜单存在子元素，无法删除");
            }
            menuSet.addAll(new HashSet<>(children));
            menuSet.add(hjmallPermissionService.findPermissionById(id));
        }
        hjmallPermissionService.delete(menuSet);
        return RestResponse.ok(ResultMsg.DEL_SUCCESS);
    }
}
