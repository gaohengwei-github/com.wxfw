package com.wxfw.model;

import com.wxfw.entity.HjmallRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Set;

/**
 * HjmallAdminVo
 *
 * @author gaohw
 * @date 2020/4/4
 */
@ApiModel("管理员model")
public class HjmallAdminVo {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("商铺ID")
    private String shopId;

    @ApiModelProperty("登录账号")
    private String account;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("联系方式")
    private String phoneNumber;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("最后一次登录IP")
    private String lastIp;

    @ApiModelProperty("最后一次登录时间")
    private Date lastTime;

    @ApiModelProperty("创建时间")
    private Date addTime;


    @ApiModelProperty("用户状态，1有效0无效")
    private boolean status;


    @ApiModelProperty("用户角色")
    private Set<HjmallRole> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<HjmallRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<HjmallRole> roles) {
        this.roles = roles;
    }
}
