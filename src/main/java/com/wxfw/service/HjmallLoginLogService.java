package com.wxfw.service;

import com.wxfw.entity.HjmallLoginLog;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-05 17:09
 **/
public interface HjmallLoginLogService {

    /**
     * 新增登录记录
     *
     * @param hjmallLoginLog
     */
    void createLoginLog(HjmallLoginLog hjmallLoginLog);

    /**
     * 登录记录的全表查找
     *
     * @param burry
     * @param createTime
     * @param page
     * @param size
     * @return
     */
    Object findAllLoginLog(String burry, String createTime, int page, int size);

}
