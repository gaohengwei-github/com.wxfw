package com.wxfw.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;

/**
 * PermissionMetaVo
 *
 * @author gaohw
 * @date 2020/3/28
 */

@ApiModel("菜单导航栏model")
public class PermissionMetaVo implements Serializable {

    @ApiModelProperty("菜单主题")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    public PermissionMetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public PermissionMetaVo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
