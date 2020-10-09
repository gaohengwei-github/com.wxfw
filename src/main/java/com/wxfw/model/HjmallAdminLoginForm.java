package com.wxfw.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * UserLoginForm
 *
 * @author gaohw
 * @date 2020/3/19
 */
@ApiModel("登录表单model")
public class HjmallAdminLoginForm {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("登录密码")
    private String password;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("验证码对应的key")
    private String uuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
