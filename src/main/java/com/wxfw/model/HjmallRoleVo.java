package com.wxfw.model;

import com.wxfw.entity.HjmallPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;


/**
 * HjmallRoleVo
 *
 * @author gaohw
 * @date 2020/3/28
 */
@ApiModel("角色与菜单关联的model")
public class HjmallRoleVo {

    @ApiModelProperty("角色ID")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色类型")
    private String level;

    @ApiModelProperty("角色类型名称")
    private String roleState;

    @ApiModelProperty("角色状态")
    private boolean status;

    @ApiModelProperty("角色所持有的权限")
    private Set<HjmallPermission> permissions;

    public String getRoleState() {
        if ("1".equals(this.level)) {
            return "菜单权限角色";
        } else {
            return "数据权限角色";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<HjmallPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<HjmallPermission> permissions) {
        this.permissions = permissions;
    }
}
