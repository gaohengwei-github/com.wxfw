package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@ApiModel("配置类详情")
@Table(name = "SYSTEM_CONFIG_DETAIL")
public class HjmallSystemConfigDetail implements Serializable {

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

    @Column(name = "type")
    @ApiModelProperty("类型(文本框,单选按钮...)")
    private String type;

    @Column(name = "input_type")
    @ApiModelProperty("表单类型")
    private String inputType;

    @Column(name = "config_id")
    @ApiModelProperty("配置分类id")
    private String configId;

    @Column(name = "parameter")
    @ApiModelProperty("规则 单选框和多选框")
    private String parameter;

    @Column(name = "value")
    @ApiModelProperty("默认值")
    private String value;

    @Column(name = "sort")
    @ApiModelProperty("排序")
    private Integer sort;

    @Column(name = "status")
    @ApiModelProperty("是否隐藏")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
