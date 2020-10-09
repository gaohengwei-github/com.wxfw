package com.wxfw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallLoginLogMapper;
import com.wxfw.entity.HjmallLoginLog;
import com.wxfw.service.HjmallLoginLogService;
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
 * HjmallLoginLogServiceImpl
 *
 * @author gaohw
 * @date 2020/4/5
 */
@Service
public class HjmallLoginLogServiceImpl implements HjmallLoginLogService {

    @Autowired
    private HjmallLoginLogMapper loginLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLoginLog(HjmallLoginLog hjmallLoginLog) {
        hjmallLoginLog.setId(StrUtils.randomUUID());
        hjmallLoginLog.setLoginTime(new Date());
        loginLogMapper.insert(hjmallLoginLog);
    }

    @Override
    public Object findAllLoginLog(String burry, String createTime, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("burry", burry);
        stringObjectMap.put("addTime", createTime);
        List<HjmallLoginLog> logList = loginLogMapper.selectByExample(createExample(stringObjectMap));
        PageInfo pageInfo = new PageInfo(logList);
        return PageUtil.toPage(pageInfo);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallLoginLog.class);
        example.setOrderByClause("login_time DESC");
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            if (searchMap.get("burry") != null && !"".equals(searchMap.get("burry"))) {
                criteria.andLike("loginName", "%" + searchMap.get("burry") + "%");
            }
            if (searchMap.get("addTime") != null && !"".equals(searchMap.get("addTime"))) {
                String[] strings = searchMap.get("addTime").toString().split(",");
                criteria.andBetween("loginTime", strings[0], strings[1]);
            }

        }
        return example;
    }
}
