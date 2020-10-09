package com.wxfw.controller;

import com.wxfw.util.annotations.*;
import com.wxfw.entity.HjmallLoginLog;
import com.wxfw.entity.HjmallOperLog;
import com.wxfw.service.HjmallLoginLogService;
import com.wxfw.service.HjmallOperLogService;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * HjmallOperLogController
 *
 * @author gaohw
 * @date 2020/4/3
 */
@RestController
@Api(tags = "系统：日志管理")
@RequestMapping("operLog")
public class HjmallOperLogController {

    @Autowired
    private HjmallOperLogService hjmallOperLogService;
    @Autowired
    private HjmallLoginLogService hjmallLoginLogService;

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("save")
    @ApiOperation(value = "新增操作日志", notes = "新增操作日志", httpMethod = "POST", tags = "系统：日志管理")
    public void addSave(@RequestBody HjmallOperLog hjmallOperLog) {
        hjmallOperLogService.insertOperlog(hjmallOperLog);
    }

    @GetMapping
    @ApiOperation(value = "操作日志查询", notes = "操作日志查询", httpMethod = "GET", tags = "系统：日志管理")
    @NeedLogin
    @NeedPermission(permissions = {"logs:list"})
    public RestResponse getLogs(@RequestParam(value = "blurry", required = false) String blurry,
                                @RequestParam(value = "createTime", required = false) String addTime,
                                @RequestParam(value = "page", required = false) String page,
                                @RequestParam(value = "size", required = false) String size) {
        Object object = hjmallOperLogService.getLogs(blurry, addTime, 0, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }

    @GetMapping(value = "/error")
    @ApiOperation(value = "异常日志查询", notes = "异常日志查询", httpMethod = "GET", tags = "系统：日志管理")
    @NeedLogin
    @NeedPermission(permissions = {"logs:list"})
    public RestResponse getErrorLogs(@RequestParam(value = "blurry", required = false) String blurry,
                                     @RequestParam(value = "createTime", required = false) String addTime,
                                     @RequestParam(value = "page", required = false) String page,
                                     @RequestParam(value = "size", required = false) String size) {
        Object object = hjmallOperLogService.getLogs(blurry, addTime, 1, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation(value = "日志异常详情查询", notes = "日志异常详情查询", httpMethod = "GET", tags = "系统：日志管理")
    @NeedLogin
    @NeedPermission(permissions = {"logs:list"})
    public RestResponse getErrorLogs(@PathVariable String id) {
        HjmallOperLog object = hjmallOperLogService.selectOperLogById(id);
        return RestResponse.ok(object);
    }

    @DeleteMapping(value = "/del/error")
    @ApiOperation(value = "删除所有ERROR日志", notes = "删除所有ERROR日志", httpMethod = "DELETE", tags = "系统：日志管理")
    @NeedPermission(permissions = {"logs:list"})
    @NeedLogin
    public RestResponse delAllByError() {
        hjmallOperLogService.cleanOperLog(1);
        return RestResponse.ok();
    }

    @DeleteMapping(value = "/del/info")
    @ApiOperation(value = "删除所有INFO日志", notes = "删除所有INFO日志", httpMethod = "DELETE", tags = "系统：日志管理")
    @NeedPermission(permissions = {"logs:list"})
    @NeedLogin
    public RestResponse delAllByInfo() {
        hjmallOperLogService.cleanOperLog(0);
        return RestResponse.ok();
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "用户日志查询", notes = "用户日志查询", httpMethod = "GET", tags = "系统：日志管理")
    @NeedLogin
    public RestResponse getUserLogs(@RequestParam(value = "page", required = false) String page,
                                    @RequestParam(value = "size", required = false) String size) {
        Object object = hjmallOperLogService.getLogsByOperName(Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }

    /**
     * 查询系统访问记录列表
     */
    @GetMapping("list")
    @ApiOperation(value = "登录日志查询", notes = "登录日志查询", httpMethod = "GET", tags = "系统：日志管理")
    @NeedLogin
    @NeedPermission(permissions = {"logs:list"})
    public RestResponse findAllLoginLog(@RequestParam(value = "blurry", required = false) String blurry,
                                        @RequestParam(value = "createTime", required = false) String addTime,
                                        @RequestParam(value = "page", required = false) String page,
                                        @RequestParam(value = "size", required = false) String size) {
        Object object = hjmallLoginLogService.findAllLoginLog(blurry, addTime, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }

    /**
     * 新增保存系统访问记录
     */
    @PostMapping("saveLog")
    @ApiOperation(value = "新增登录日志", notes = "新增登录日志", httpMethod = "POST", tags = "系统：日志管理")
    public void addSave(@RequestBody HjmallLoginLog hjmallLoginLog) {
        hjmallLoginLogService.createLoginLog(hjmallLoginLog);
    }
}
