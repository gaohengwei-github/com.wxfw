package com.wxfw.model;

import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * HjmallOperLogVo
 *
 * @author gaohw
 * @date 2020/4/5
 */
@ApiIgnore
public class HjmallOperLogVo {
    private String id;
    private String title;
    private int businessType;
    private String method;
    private String requestMethod;
    private int operatorType;
    private String operName;
    private String deptName;
    private String operUrl;
    private String operIp;
    private String operLocation;
    private String operParam;
    private int status;
    private String errorMsg;
    private Date operTime;
    private String account;
    private String operatorTypeName;
    private String businessTypeName;
    private String browser;
    private Long time;


    public String getOperatorTypeName() {
        String name = "其他";
        if (this.operatorType == 1) {
            name = "后台用户";
        } else if (this.operatorType == 2) {
            name = "手机端用户";
        }
        return name;
    }

    public String getBusinessTypeName() {
        if (this.businessType == 1) {
            return "新增";
        } else if (this.businessType == 2) {
            return "修改";
        } else if (this.businessType == 3) {
            return "删除";
        } else if (this.businessType == 4) {
            return "授权";
        } else if (this.businessType == 5) {
            return "导出";
        } else if (this.businessType == 6) {
            return "导入";
        } else if (this.businessType == 7) {
            return "强退";
        } else if (this.businessType == 8) {
            return "生成代码";
        } else if (this.businessType == 9) {
            return "清空";
        } else {
            return "其他";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
