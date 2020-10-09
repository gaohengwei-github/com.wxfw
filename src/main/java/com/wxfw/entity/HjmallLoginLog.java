package com.wxfw.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SYSTEM_LOGIN_LOG")
@ApiModel("登录日志")
@Entity
public class HjmallLoginLog implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "login_name")
    @ApiModelProperty("登录账号")
    private String loginName;

    @Column(name = "ipaddr")
    @ApiModelProperty("ip地址")
    private String ipaddr;

    @Column(name = "login_location")
    @ApiModelProperty("IP所属区域")
    private String loginLocation;

    @Column(name = "browser")
    @ApiModelProperty("浏览器")
    private String browser;

    @Column(name = "os")
    @ApiModelProperty("操作系统")
    private String os;

    @Column(name = "status")
    @ApiModelProperty("登录状态（0成功 1失败）")
    private String status;

    @Column(name = "msg")
    @ApiModelProperty("提示信息")
    private String msg;

    @Column(name = "login_time")
    @ApiModelProperty("登录时间")
    private Date loginTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
