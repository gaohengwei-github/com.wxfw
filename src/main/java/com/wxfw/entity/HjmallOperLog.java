package com.wxfw.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SYSTEM_OPER_LOG")
@ApiModel("操作日志实体类")
@Entity
public class HjmallOperLog implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "title")
    @ApiModelProperty("日志描述")
    private String title;

    @Column(name = "business_type")
    @ApiModelProperty("日志类型")
    private Integer businessType;

    @Column(name = "method")
    @ApiModelProperty("请求路由")
    private String method;

    @Column(name = "request_method")
    @ApiModelProperty("请求方式")
    private String requestMethod;

    @Column(name = "operator_type")
    @ApiModelProperty("操作人用户类型")
    private Integer operatorType;

    @Column(name = "oper_name")
    @ApiModelProperty("操作人ID")
    private String operName;

    @Column(name = "dept_name")
    @ApiModelProperty("操作人账号")
    private String deptName;

    @Column(name = "oper_url")
    @ApiModelProperty("请求方法")
    private String operUrl;

    @Column(name = "oper_ip")
    @ApiModelProperty("IP地址")
    private String operIp;

    @Column(name = "oper_location")
    @ApiModelProperty("IP所属区域")
    private String operLocation;

    @Column(name = "oper_param")
    @ApiModelProperty("请求参数")
    private String operParam;

    @Column(name = "status")
    @ApiModelProperty("操作状态（0正常 1异常）")
    private Integer status;

    @Column(name = "error_msg")
    @ApiModelProperty("错误信息")
    private String errorMsg;

    @Column(name = "oper_time")
    @ApiModelProperty("创建时间")
    private Date operTime;

    @Column(name = "browser")
    @ApiModelProperty("浏览器")
    private String browser;

    @Column(name = "time")
    @ApiModelProperty("请求耗时")
    private Long time;

    @Transient
    private String operatorTypeName;

    @Transient
    private String businessTypeName;

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

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
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

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

}
