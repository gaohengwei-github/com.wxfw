package com.wxfw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallOperLogMapper;
import com.wxfw.entity.HjmallOperLog;
import com.wxfw.util.Constant.Applications;
import com.wxfw.service.HjmallOperLogService;
import com.wxfw.util.PageUtil;
import com.wxfw.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HjmallOperLogServiceImpl
 *
 * @author gaohw
 * @date 2020/4/3
 */
@Service
public class HjmallOperLogServiceImpl implements HjmallOperLogService {

    @Autowired
    private HjmallOperLogMapper hjmallOperLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOperlog(HjmallOperLog operLog) {
        operLog.setId(StrUtils.randomUUID());
        operLog.setOperTime(new Date());
        hjmallOperLogMapper.insertSelective(operLog);
    }

    @Override
    public Object getLogs(String blurry, String createTime, int status, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("blurry", blurry);
        stringObjectMap.put("createTime", createTime);
        stringObjectMap.put("status", status);
        List<HjmallOperLog> logList = hjmallOperLogMapper.selectByExample(createExample(stringObjectMap));
//        List<HjmallOperLogVo> logVoArrayList = new ArrayList<>();
//        for (HjmallOperLog hjmallOperLog:logList) {
//            HjmallOperLogVo hjmallOperLogVo = new HjmallOperLogVo();
//            BeanUtils.copyProperties(hjmallOperLog,hjmallOperLogVo);
//            logVoArrayList.add(hjmallOperLogVo);
//        }
        PageInfo pageInfo = new PageInfo(logList);
        return PageUtil.toPage(pageInfo);
    }

    @Override
    public Object getLogsByOperName(int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("operName", Applications.getCurrentUser().getUid());
        List<HjmallOperLog> logList = hjmallOperLogMapper.selectByExample(createExample(stringObjectMap));
//        List<HjmallOperLogVo> logVoArrayList = new ArrayList<>();
//        for (HjmallOperLog hjmallOperLog:logList) {
//            HjmallOperLogVo hjmallOperLogVo = new HjmallOperLogVo();
//            BeanUtils.copyProperties(hjmallOperLog,hjmallOperLogVo);
//            logVoArrayList.add(hjmallOperLogVo);
//        }
        PageInfo pageInfo = new PageInfo(logList);
        return PageUtil.toPage(pageInfo);
    }

    @Override
    public HjmallOperLog selectOperLogById(String operId) {
        return hjmallOperLogMapper.selectByPrimaryKey(operId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanOperLog(int status) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("status", status);
        hjmallOperLogMapper.deleteByExample(createExample(stringObjectMap));
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallOperLog.class);
        example.setOrderByClause("oper_time DESC");
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 组件地址
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            if (searchMap.get("createTime") != null && !"".equals(searchMap.get("createTime"))) {
                String[] strings = searchMap.get("createTime").toString().split(",");
                criteria.andBetween("operTime", strings[0], strings[1]);
            }
            if (searchMap.get("operName") != null && !"".equals(searchMap.get("operName"))) {
                criteria.andEqualTo("operName", searchMap.get("operName"));
            }
            if (searchMap.get("blurry") != null && !"".equals(searchMap.get("blurry"))) {
                criteria.andLike("deptName", searchMap.get("blurry").toString()).
                        orLike("title", searchMap.get("blurry").toString());
            }
        }
        return example;
    }
}
