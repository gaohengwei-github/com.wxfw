package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ApiModel("角色表")
@Table(name = "SYSTEM_ROLE")
@Entity
public class HjmallRole implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(name = "主键")
    private String id;

    @Column(name = "role_name")
    @ApiModelProperty("角色名称")
    private String roleName;

    @Column(name = "level")
    @ApiModelProperty("角色类型，1菜单角色 2数据角色")
    private String level;

    @ApiModelProperty("状态1有效0无效")
    @Column(name = "status")
    private Boolean status;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
