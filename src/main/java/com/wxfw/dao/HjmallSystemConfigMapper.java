package com.wxfw.dao;

import com.wxfw.entity.HjmallSystemConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-11 22:46
 **/
@Repository(value = "HjmallSystemConfigMapper")
public interface HjmallSystemConfigMapper extends Mapper<HjmallSystemConfig> {


}
