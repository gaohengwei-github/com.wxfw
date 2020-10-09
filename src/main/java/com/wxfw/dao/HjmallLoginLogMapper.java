package com.wxfw.dao;

import com.wxfw.entity.HjmallLoginLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-03 22:30
 **/
@Repository(value = "HjmallLoginLogMapper")
public interface HjmallLoginLogMapper extends Mapper<HjmallLoginLog> {


}
