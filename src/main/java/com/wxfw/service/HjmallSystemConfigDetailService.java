package com.wxfw.service;

import com.wxfw.entity.HjmallSystemConfigDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-12 22:10
 **/
public interface HjmallSystemConfigDetailService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    HjmallSystemConfigDetail findById(String id);


    /**
     * 根据configID查询详情信息
     *
     * @param configId
     * @return
     */
    List<HjmallSystemConfigDetail> findAllByConfigId(String configId);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    void create(HjmallSystemConfigDetail resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(HjmallSystemConfigDetail resources);

    /**
     * 删除
     *
     * @param id /
     */
    void delete(String id);

    /**
     * 分页查询
     *
     * @param blurry 条件
     * @return /
     */
    Object queryAll(String blurry, String configId, int page, int size);


    /**
     * 判断该配置信息是否已存在
     *
     * @param title
     * @return
     */
    boolean exsitTitle(String title, String configId);

    /**
     * 判断名称是否已存在
     *
     * @param menuName
     * @return
     */
    boolean exsitMenuName(String menuName, String configId);


    /**
     * 查询配置信息
     *
     * @param stringObjectMap
     * @return
     */
    Object searchSystemConfig(Map<String, Object> stringObjectMap);

}
