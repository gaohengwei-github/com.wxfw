package com.wxfw.dao;

import com.wxfw.entity.HjmallSystemConfigDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-12 22:09
 **/
@Repository(value = "HjmallSystemConfigDetailMapper")
public interface HjmallSystemConfigDetailMapper extends Mapper<HjmallSystemConfigDetail> {

    /**
     * 模糊搜索
     *
     * @param stringObjectMap
     * @return
     */
    List<HjmallSystemConfigDetail> searchSystemConfigDetail(Map<String, Object> stringObjectMap);
}
