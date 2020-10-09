package com.wxfw.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * HjmallPermissionVo
 *
 * @author gaohw
 * @date 2020/3/28
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel("权限Model")
public class HjmallPermissionVo implements Serializable {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路由")
    private String path;

    @ApiModelProperty("是否显示")
    private Boolean hidden;

    @ApiModelProperty("重定向路由")
    private String redirect;

    @ApiModelProperty("组件地址")
    private String component;

    @ApiModelProperty("是否一直显示")
    private Boolean alwaysShow;


    private PermissionMetaVo meta;

    private List<HjmallPermissionVo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public PermissionMetaVo getMeta() {
        return meta;
    }

    public void setMeta(PermissionMetaVo meta) {
        this.meta = meta;
    }

    public List<HjmallPermissionVo> getChildren() {
        return children;
    }

    public void setChildren(List<HjmallPermissionVo> children) {
        this.children = children;
    }
}
