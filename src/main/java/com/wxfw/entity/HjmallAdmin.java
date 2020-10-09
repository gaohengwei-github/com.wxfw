package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@ApiModel("后台管理员表")
@Table(name = "SYSTEM_ADMIN")
@Entity
public class HjmallAdmin implements Serializable {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("登录账号")
    @Column(name = "account")
    private String account;

    @ApiModelProperty("密码")
    @Column(name = "pwd")
    private String pwd;

    @ApiModelProperty("头像")
    @Column(name = "icon")
    private String icon;

    @ApiModelProperty("真实姓名")
    @Column(name = "real_name")
    private String realName;

    @ApiModelProperty("邮箱")
    @Column(name = "email")
    private String email;

    @ApiModelProperty("联系方式")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ApiModelProperty("最后一次登录IP")
    @Column(name = "last_ip")
    private String lastIp;

    @ApiModelProperty("最后一次登录时间")
    @Column(name = "last_time")
    private Date lastTime;

    @ApiModelProperty("创建时间")
    @Column(name = "add_time")
    private Date addTime;


    @ApiModelProperty("用户状态，1有效0无效")
    @Column(name = "status")
    private Boolean status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
