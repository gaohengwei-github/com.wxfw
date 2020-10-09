package com.wxfw.controller;


import com.wxfw.util.annotations.*;
import com.wxfw.entity.HjmallSystemConfigDetail;
import com.wxfw.util.eums.BusinessType;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.service.HjmallSystemConfigDetailService;
import com.wxfw.util.ResultMsg;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * HjmallSystemConfigDetailController
 *
 * @author gaohw
 * @date 2020/4/12
 */
@RestController
@Api(tags = "系统：配置详情")
@RequestMapping("/configDetail")
public class HjmallSystemConfigDetailController {

    @Autowired
    private HjmallSystemConfigDetailService hjmallSystemConfigDetailService;


    @GetMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:list"})
    @ApiOperation(value = "配置详情查询接口", notes = "配置详情查询接口", httpMethod = "GET", tags = "系统：配置详情")
    public RestResponse getSystemConfigDetails(@RequestParam(value = "page", required = false) String page,
                                               @RequestParam(value = "size", required = false) String size,
                                               @RequestParam(value = "configId") String configId,
                                               @RequestParam(value = "blurry", required = false) String blurry) {
        Object object = hjmallSystemConfigDetailService.queryAll(blurry, configId, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }


    @PostMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:add"})
    @OperLog(title = "增加配置详情", businessType = BusinessType.INSERT)
    @ApiOperation(value = "配置详情新增接口", notes = "配置详情新增接口", httpMethod = "POST", tags = "系统：配置详情")
    public RestResponse add(@Validated @RequestBody HjmallSystemConfigDetail hjmallSystemConfig) {
        if (hjmallSystemConfigDetailService.exsitTitle(hjmallSystemConfig.getName(), hjmallSystemConfig.getConfigId())) {
            throw new ServiceRuntimeException("该配置名称已存在");
        }
        if (hjmallSystemConfigDetailService.exsitMenuName(hjmallSystemConfig.getMenuName(), hjmallSystemConfig.getConfigId())) {
            throw new ServiceRuntimeException("该配置属性参数已存在");
        }
        hjmallSystemConfigDetailService.create(hjmallSystemConfig);
        return RestResponse.ok(ResultMsg.ADD_SUCCESS);
    }

    @PutMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:edit"})
    @OperLog(title = "编辑配置详情", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "配置详情编辑接口", notes = "配置详情编辑接口", httpMethod = "POST", tags = "系统：配置详情")
    public RestResponse edit(@Validated @RequestBody HjmallSystemConfigDetail hjmallSystemConfig) {
        HjmallSystemConfigDetail systemConfig = hjmallSystemConfigDetailService.findById(hjmallSystemConfig.getId());
        if (!systemConfig.getName().equals(hjmallSystemConfig.getName())) {
            if (hjmallSystemConfigDetailService.exsitTitle(hjmallSystemConfig.getName(), hjmallSystemConfig.getConfigId())) {
                throw new ServiceRuntimeException("该配置名称已存在");
            }
        }
        if (!systemConfig.getMenuName().equals(hjmallSystemConfig.getMenuName())) {
            if (hjmallSystemConfigDetailService.exsitMenuName(hjmallSystemConfig.getMenuName(), hjmallSystemConfig.getConfigId())) {
                throw new ServiceRuntimeException("该配置属性参数已存在");
            }
        }
        BeanUtils.copyProperties(hjmallSystemConfig, systemConfig);
        hjmallSystemConfigDetailService.update(systemConfig);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/{id}")
    @NeedPermission(permissions = {"config:del"})
    @OperLog(title = "删除配置详情", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除配置详情信息", notes = "删除配置详情信息", httpMethod = "DELETE", tags = "系统：配置详情")
    public RestResponse delete(@PathVariable String id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        hjmallSystemConfigDetailService.delete(id);
        return RestResponse.ok(ResultMsg.DEL_SUCCESS);
    }

    @PostMapping(value = "searchSystemConfigDetail")
    @ApiOperation(value = "模糊配置详情信息", notes = "模糊配置详情信息", httpMethod = "DELETE", tags = "系统：配置详情")
    public RestResponse searchSystemConfigDetail(@RequestBody Map<String, Object> stringObjectMap) {
        Object hjmallSystemConfigDetails = hjmallSystemConfigDetailService.searchSystemConfig(stringObjectMap);
        return RestResponse.ok(hjmallSystemConfigDetails);

    }
}
