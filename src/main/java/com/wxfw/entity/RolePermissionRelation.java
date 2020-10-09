package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel("角色权限关联表")
@Table(name = "SYSTEM_ROLE_PERMISSION_RELATION")
@Entity
public class RolePermissionRelation implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("角色ID")
    @Column(name = "role_id")
    private String roleId;

    @ApiModelProperty("权限ID")
    @Column(name = "permission_id")
    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public RolePermissionRelation(String id, String roleId, String permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public RolePermissionRelation() {
    }
}
