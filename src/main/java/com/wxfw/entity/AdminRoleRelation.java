package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@ApiModel("管理员与角色关联表")
@Table(name = "SYSTEM_ADMIN_ROLE_RELATION")
@Entity
public class AdminRoleRelation implements Serializable {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("管理员ID")
    @Column(name = "admin_id")
    private String adminId;

    @ApiModelProperty("角色ID")
    @Column(name = "role_id")
    private String roleId;

    @ApiModelProperty("到期时间")
    @Column(name = "due_date")
    private Date dueDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
