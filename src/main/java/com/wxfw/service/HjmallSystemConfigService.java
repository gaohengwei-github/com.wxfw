package com.wxfw.service;

import com.wxfw.entity.HjmallSystemConfig;

import java.util.List;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-11 23:00
 **/
public interface HjmallSystemConfigService {


    /**
     * 分页查询
     *
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    Object findAllConfigInfo(String name, int page, int pageSize);


    /**
     * 根据类型查询配置
     *
     * @param module
     * @return
     */
    List<HjmallSystemConfig> findAllByModule(String module);


    /**
     * 添加配置信息
     *
     * @param hjmallSystemConfig
     */
    void add(HjmallSystemConfig hjmallSystemConfig);


    /**
     * 根据主键查询信息
     *
     * @param id
     * @return
     */
    HjmallSystemConfig findById(String id);


    /**
     * 判断该配置信息是否已存在
     *
     * @param title
     * @return
     */
    boolean exsitTitle(String title);

    /**
     * 判断名称是否已存在
     *
     * @param menuName
     * @return
     */
    boolean exsitMenuName(String menuName);

    /**
     * 修改配置信息
     *
     * @param resources
     */
    void update(HjmallSystemConfig resources);

    /**
     * 删除信息
     *
     * @param id
     */
    void delete(String id);


}
