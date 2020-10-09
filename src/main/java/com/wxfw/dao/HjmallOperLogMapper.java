package com.wxfw.dao;

import com.wxfw.entity.HjmallOperLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-03 22:46
 **/
@Repository(value = "HjmallOperLogMapper")
public interface HjmallOperLogMapper extends Mapper<HjmallOperLog> {


}
