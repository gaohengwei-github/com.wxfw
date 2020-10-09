package com.wxfw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallSystemConfigDetailMapper;
import com.wxfw.entity.HjmallSystemConfigDetail;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallSystemConfigDetailService;
import com.wxfw.util.PageUtil;
import com.wxfw.util.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HjmallSystemConfigDetailServiceImpl
 *
 * @author gaohw
 * @date 2020/4/12
 */
@Service
public class HjmallSystemConfigDetailServiceImpl implements HjmallSystemConfigDetailService {

    @Autowired
    private HjmallSystemConfigDetailMapper hjmallSystemConfigDetailMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public HjmallSystemConfigDetail findById(String id) {
        return hjmallSystemConfigDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HjmallSystemConfigDetail resources) {
        resources.setId(StrUtils.randomUUID());
        hjmallSystemConfigDetailMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HjmallSystemConfigDetail resources) {
        HjmallSystemConfigDetail hjmallSystemConfigDetail = findById(resources.getId());
        BeanUtils.copyProperties(resources, hjmallSystemConfigDetail);
        hjmallSystemConfigDetailMapper.updateByPrimaryKeySelective(hjmallSystemConfigDetail);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        hjmallSystemConfigDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Object queryAll(String blurry, String configId, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name", blurry);
        stringObjectMap.put("configId", configId);
        List<HjmallSystemConfigDetail> configs = hjmallSystemConfigDetailMapper.selectByExample(createExample(stringObjectMap));
        PageInfo pageInfo = new PageInfo(configs);
        return PageUtil.toPage(pageInfo);
    }

    @Override
    public boolean exsitTitle(String name, String configId) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name", name);
        stringObjectMap.put("configId", configId);
        return hjmallSystemConfigDetailMapper.selectCountByExample(createExample(stringObjectMap)) > 0;
    }

    @Override
    public boolean exsitMenuName(String menuName, String configId) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("menuName", menuName);
        stringObjectMap.put("configId", configId);
        return hjmallSystemConfigDetailMapper.selectCountByExample(createExample(stringObjectMap)) > 0;
    }

    @Override
    public Object searchSystemConfig(Map<String, Object> stringObjectMap) {
        List<HjmallSystemConfigDetail> hjmallSystemConfigs = hjmallSystemConfigDetailMapper.searchSystemConfigDetail(stringObjectMap);
        if (!hjmallSystemConfigs.isEmpty()) {
            for (HjmallSystemConfigDetail hjmallSystemConfigDetail : hjmallSystemConfigs) {
                stringObjectMap.put(hjmallSystemConfigDetail.getMenuName(), hjmallSystemConfigDetail.getValue());
            }
            return stringObjectMap;
        }
        return hjmallSystemConfigs;
    }


    @Override
    public List<HjmallSystemConfigDetail> findAllByConfigId(String configId) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("configId", configId);
        stringObjectMap.put("status", true);
        return hjmallSystemConfigDetailMapper.selectByExample(createExample(stringObjectMap));
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallSystemConfigDetail.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort ASC");
        if (searchMap != null) {
            // 组件地址
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", searchMap.get("name").toString());
            }
            if (searchMap.get("menuName") != null && !"".equals(searchMap.get("menuName"))) {
                criteria.andEqualTo("menuName", searchMap.get("menuName"));
            }
            if (searchMap.get("configId") != null && !"".equals(searchMap.get("configId"))) {
                criteria.andEqualTo("configId", searchMap.get("configId"));
            }
        }
        return example;
    }


}
