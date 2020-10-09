package com.wxfw.util.Constant;

import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CurrentUser
 *
 * @author gaohw
 * @Description:保存在token中的用户信息
 * @date 2020/3/17
 */
public class CurrentUser  extends HashMap<String, Object> {

    private String uid;
    private String type;
    private String account;
    private Date loginTime;
    private Integer period;
    private boolean loggedIn;
    private List<String> roles;
    private List<String> permissions;

    @JsonIgnore
    private String token;

    public enum TYPE {
        LOGIN_USER, TMP_USER, OPERATOR, FUNCTIONAL_ID
    }

    public CurrentUser() {
    }

    public CurrentUser(Map<String, Object> fields) {
        super(fields);
    }

    public boolean isLoggedIn(){
        return !this.getType().equals(TYPE.TMP_USER.name());
    }

    public synchronized String generateToken(String secret){
        if(token == null){
            token = CurrentUserFactory.createToken(this , secret);
        }
        return token;
    }

    public String getAccount() {
        return (String) this.get("account");
    }

    public void setAccount(String account) {
        this.put("account", account);
    }

    public String getUid() {
        return (String) this.get("uid");
    }

    public void setUid(String uid) {
        this.put("uid", uid);
    }

    public Date getLoginTime() {
        return (Date) this.get("loginTime");
    }

    public void setLoginTime(Date loginTime) {
        this.put("loginTime", loginTime);
    }

    public Integer getPeriod() {
        return (Integer) this.get("period");
    }

    public void setPeriod(Integer period) {
        this.put("period", period);
    }

    public String getType() {
        return (String) this.get("type");
    }

    public void setType(CurrentUser.TYPE type) {
        this.put("type", type.name());
    }

    public List<String> getRoles() {
        return (List<String>) this.get("roles");
    }

    public void setRoles(List<String> roles) {
        this.put("roles", roles);
    }

    public List<String> getPermissions() {
        return (List<String>) this.get("permissions");
    }

    public void setPermissions(List<String> permissions) {
        this.put("permissions", permissions);
    }


}
