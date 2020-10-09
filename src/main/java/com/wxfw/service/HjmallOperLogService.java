package com.wxfw.service;

import com.wxfw.entity.HjmallOperLog;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-03 22:48
 **/
public interface HjmallOperLogService {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(HjmallOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @return 操作日志集合
     */
    Object getLogs(String blurry, String createTime, int status, int page, int size);


    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    HjmallOperLog selectOperLogById(String operId);


    Object getLogsByOperName(int page, int size);

    /**
     * 清空操作日志
     */
    void cleanOperLog(int status);
}
