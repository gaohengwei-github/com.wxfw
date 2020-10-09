package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@ApiModel("系统配置类")
@Table(name = "SYSTEM_CONFIG")
public class HjmallSystemConfig implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "name")
    @ApiModelProperty("配置名称")
    private String name;

    @Column(name = "menu_name")
    @ApiModelProperty("字段变量")
    private String menuName;

    @Column(name = "sort")
    @ApiModelProperty("排序")
    private Integer sort;

    @Column(name = "module")
    @ApiModelProperty("1 系统 2应用 3 支付 4 其他")
    private String module;

    @Column(name = "status")
    @ApiModelProperty("是否有效，0无效，1有效")
    private Boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
