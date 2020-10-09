package com.wxfw.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("权限表")
@Table(name = "SYSTEM_PERMISSION")
@Entity
public class HjmallPermission implements Serializable {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("父ID")
    @Column(name = "pid")
    private String pid;

    @ApiModelProperty("菜单名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("预留字段")
    @Column(name = "value")
    private String value;

    @ApiModelProperty("图标")
    @Column(name = "icon")
    private String icon;

    @ApiModelProperty("链接地址")
    @Column(name = "path")
    private String path;

    @ApiModelProperty("类型：0 目录 1菜单 2按钮")
    @Column(name = "type")
    private String type;

    @ApiModelProperty("前端组件地址")
    @Column(name = "component")
    private String component;

    @ApiModelProperty("权限")
    @Column(name = "permission")
    private String permission;

    @ApiModelProperty("是否有效 1是0否")
    @Column(name = "status")
    private Boolean status;

    @ApiModelProperty("创建时间")
    @Column(name = "add_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date addTime;

    @ApiModelProperty("序号")
    @Column(name = "sort")
    private String sort;

    @ApiModelProperty("是否可见 1是0否")
    @Column(name = "hidden")
    private Boolean hidden;

    @Transient
    private List<HjmallPermission> children;

    public String getIcon() {
        if (icon == null) {
            icon = "";
        }
        return icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public List<HjmallPermission> getChildren() {
        return children;
    }

    public void setChildren(List<HjmallPermission> children) {
        this.children = children;
    }
}
