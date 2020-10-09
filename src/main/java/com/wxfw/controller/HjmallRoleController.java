package com.wxfw.controller;

import com.wxfw.model.HjmallRoleVo;
import com.wxfw.util.annotations.*;
import com.wxfw.entity.HjmallRole;
import com.wxfw.util.eums.BusinessType;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.service.HjmallRoleService;
import com.wxfw.util.ResultMsg;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * HjmallRoleController
 *
 * @author gaohw
 * @date 2020/3/24
 */
@Api(tags = "系统：角色管理")
@RestController
@RequestMapping("/roles")
public class HjmallRoleController {

    @Autowired
    private HjmallRoleService hjmallRoleService;


    @GetMapping(value = "/{id}")
    @NeedLogin
    @ApiOperation(value = "根据主键查询角色", notes = "根据主键查询角色", httpMethod = "GET", tags = "系统：角色管理")
    @NeedPermission(permissions = {"roles:list"})
    public RestResponse getRoles(@PathVariable String id) {
        return RestResponse.ok(hjmallRoleService.findRoleById(id));
    }


    @GetMapping
    @NeedLogin
    @ApiOperation(value = "查询角色", notes = "查询角色", httpMethod = "GET", tags = "系统：角色管理")
    @NeedPermission(permissions = {"roles:list"})
    public RestResponse getMenusTree(@RequestParam(value = "page", required = false) String page,
                                     @RequestParam(value = "size", required = false) String size,
                                     @RequestParam(value = "blurry", required = false) String roleName) {
        Object object = hjmallRoleService.findAllRole(roleName, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }


    @GetMapping(value = "/all")
    @NeedPermission(permissions = {"user:add", "user:edit"})
    @ApiOperation(value = "查询有效角色", notes = "查询有效角色", httpMethod = "GET", tags = "系统：角色管理")
    public RestResponse getAll() {
        List<HjmallRole> hjmallRoles = hjmallRoleService.findRoleByStatus(true);
        return RestResponse.ok(hjmallRoles);
    }


    @PostMapping
    @NeedPermission(permissions = {"roles:add"})
    @NeedLogin
    @ApiOperation(value = "增加角色", notes = "增加角色", httpMethod = "POST", tags = "系统：角色管理")
    @OperLog(title = "增加角色", businessType = BusinessType.INSERT)
    public RestResponse add(@Validated @RequestBody HjmallRole hjmallRole) {
        //菜单名字和组件名称唯一
        if (hjmallRoleService.exsitByRoleName(hjmallRole.getRoleName())) {
            throw new ServiceRuntimeException("该角色已存在");
        }
        hjmallRoleService.create(hjmallRole);
        return RestResponse.ok(ResultMsg.ADD_SUCCESS);
    }


    @PutMapping
    @NeedPermission(permissions = {"roles:edit"})
    @NeedLogin
    @ApiOperation(value = "修改角色", notes = "修改角色", httpMethod = "PUT", tags = "系统：角色管理")
    @OperLog(title = "修改角色", businessType = BusinessType.UPDATE)
    public RestResponse update(@Validated @RequestBody HjmallRole hjmallRole) {
        HjmallRole hjmallRole1 = hjmallRoleService.findRoleById(hjmallRole.getId());
        //菜单名字和组件名称唯一
        if (hjmallRoleService.exsitByRoleName(hjmallRole.getRoleName())
                && !hjmallRole.getRoleName().equals(hjmallRole1.getRoleName())) {
            throw new ServiceRuntimeException("该角色已存在");
        }
        hjmallRoleService.update(hjmallRole);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @PutMapping(value = "/menu")
    @NeedLogin
    @ApiOperation(value = "修改角色菜单", notes = "修改角色菜单", httpMethod = "PUT", tags = "系统：角色管理")
    @NeedPermission(permissions = {"roles:edit"})
    @OperLog(title = "修改角色菜单关联关系", businessType = BusinessType.UPDATE)
    public RestResponse getRolePermission(@RequestBody HjmallRoleVo hjmallRoleVo) {
        hjmallRoleService.updateMenu(hjmallRoleVo);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }


    @DeleteMapping
    @OperLog(title = "删除角色", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "DELETE", tags = "系统：角色管理")
    @NeedPermission(permissions = {"roles:del"})
    public RestResponse delete(@RequestBody Set<String> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        int count = 0;
        for (String id : ids) {
            count = hjmallRoleService.findByRoleId(id);
        }
        if (count > 0) {
            throw new ServiceRuntimeException("该角色已经被用户使用，无法删除");
        } else {
            hjmallRoleService.delete(ids);
            return RestResponse.ok(ResultMsg.DEL_SUCCESS);
        }
    }
}
