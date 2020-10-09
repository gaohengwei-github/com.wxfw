package com.wxfw.controller;

import com.wxfw.util.annotations.*;
import com.wxfw.entity.HjmallSystemConfig;
import com.wxfw.entity.HjmallSystemConfigDetail;
import com.wxfw.util.eums.BusinessType;
import com.wxfw.util.exception.ServiceRuntimeException;
import com.wxfw.service.HjmallSystemConfigDetailService;
import com.wxfw.service.HjmallSystemConfigService;
import com.wxfw.util.ResultMsg;
import com.wxfw.util.web.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HjmallSystemConfigController
 *
 * @author gaohw
 * @date 2020/4/11
 */
@RestController
@RequestMapping("/config")
@Api(tags = "系统：配置")
public class HjmallSystemConfigController {

    @Autowired
    private HjmallSystemConfigService hjmallSystemConfigService;
    @Autowired
    private HjmallSystemConfigDetailService hjmallSystemConfigDetailService;

    @GetMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:list"})
    @ApiOperation(value = "配置查询接口", notes = "配置查询接口", httpMethod = "GET", tags = "系统：配置")
    public RestResponse getSystemConfigs(@RequestParam(value = "page", required = false) String page,
                                         @RequestParam(value = "size", required = false) String size,
                                         @RequestParam(value = "blurry", required = false) String blurry) {
        Object object = hjmallSystemConfigService.findAllConfigInfo(blurry, Integer.valueOf(page) + 1, Integer.valueOf(size));
        return RestResponse.ok(object);
    }

    @GetMapping("/commen")
    @NeedLogin
    @NeedPermission(permissions = {"config:list"})
    @ApiOperation(value = "查询通用配置接口", notes = "查询通用配置接口", httpMethod = "GET", tags = "系统：配置")
    public RestResponse getCommSystemConfigs() {
        List<HjmallSystemConfig> object = hjmallSystemConfigService.findAllByModule("1");
        return RestResponse.ok(object);
    }


    @PostMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:add"})
    @OperLog(title = "增加配置", businessType = BusinessType.INSERT)
    @ApiOperation(value = "配置新增接口", notes = "配置新增接口", httpMethod = "POST", tags = "系统：配置")
    public RestResponse add(@Validated @RequestBody HjmallSystemConfig hjmallSystemConfig) {
        if (hjmallSystemConfigService.exsitTitle(hjmallSystemConfig.getName())) {
            throw new ServiceRuntimeException("该配置名称已存在");
        }
        if (hjmallSystemConfigService.exsitMenuName(hjmallSystemConfig.getMenuName())) {
            throw new ServiceRuntimeException("该配置属性参数已存在");
        }
        hjmallSystemConfigService.add(hjmallSystemConfig);
        return RestResponse.ok(ResultMsg.ADD_SUCCESS);
    }

    @PutMapping
    @NeedLogin
    @NeedPermission(permissions = {"config:edit"})
    @OperLog(title = "编辑配置", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "配置编辑接口", notes = "配置编辑接口", httpMethod = "POST", tags = "系统：配置")
    public RestResponse edit(@Validated @RequestBody HjmallSystemConfig hjmallSystemConfig) {
        HjmallSystemConfig systemConfig = hjmallSystemConfigService.findById(hjmallSystemConfig.getId());
        if (!systemConfig.getName().equals(hjmallSystemConfig.getName())) {
            if (hjmallSystemConfigService.exsitTitle(hjmallSystemConfig.getName())) {
                throw new ServiceRuntimeException("该配置名称已存在");
            }
        }
        if (!systemConfig.getMenuName().equals(hjmallSystemConfig.getMenuName())) {
            if (hjmallSystemConfigService.exsitMenuName(hjmallSystemConfig.getMenuName())) {
                throw new ServiceRuntimeException("该配置属性参数已存在");
            }
        }
        BeanUtils.copyProperties(hjmallSystemConfig, systemConfig);
        hjmallSystemConfigService.update(systemConfig);
        return RestResponse.ok(ResultMsg.UPDATE_SUCCESS);
    }

    @DeleteMapping(value = "/{id}")
    @NeedPermission(permissions = {"config:del"})
    @ApiOperation(value = "删除配置信息", notes = "删除配置信息", httpMethod = "DELETE", tags = "系统：配置")
    @OperLog(title = "删除配置", businessType = BusinessType.DELETE)
    public RestResponse delete(@PathVariable String id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        List<HjmallSystemConfigDetail> children = hjmallSystemConfigDetailService.findAllByConfigId(id);
        if (children != null && !children.isEmpty()) {
            throw new ServiceRuntimeException("该配置存在子属性，无法删除");
        }
        hjmallSystemConfigService.delete(id);
        return RestResponse.ok(ResultMsg.DEL_SUCCESS);
    }
}
