package com.wxfw.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallSystemConfigMapper;
import com.wxfw.entity.HjmallSystemConfig;
import com.wxfw.service.HjmallSystemConfigService;
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
 * HjmallSystemConfigServiceImpl
 *
 * @author gaohw
 * @date 2020/4/11
 */
@Service
public class HjmallSystemConfigServiceImpl implements HjmallSystemConfigService {

    @Autowired
    private HjmallSystemConfigMapper hjmallSystemConfigMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HjmallSystemConfig hjmallSystemConfig) {
        hjmallSystemConfig.setId(StrUtils.randomUUID());
        hjmallSystemConfigMapper.insert(hjmallSystemConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HjmallSystemConfig resources) {
        HjmallSystemConfig hjmallSystemConfig = hjmallSystemConfigMapper.selectByPrimaryKey(resources.getId());
        if (hjmallSystemConfig != null) {
            BeanUtils.copyProperties(resources, hjmallSystemConfig);
            hjmallSystemConfigMapper.updateByPrimaryKey(hjmallSystemConfig);
        }
    }

    @Override
    public List<HjmallSystemConfig> findAllByModule(String module) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("module", module);
        stringObjectMap.put("status", true);
        return hjmallSystemConfigMapper.selectByExample(createExample(stringObjectMap));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        hjmallSystemConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Object findAllConfigInfo(String name, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name", name);
        List<HjmallSystemConfig> configs = hjmallSystemConfigMapper.selectByExample(createExample(stringObjectMap));
        PageInfo pageInfo = new PageInfo(configs);
        return PageUtil.toPage(pageInfo);
    }

    @Override
    public HjmallSystemConfig findById(String id) {
        return hjmallSystemConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean exsitTitle(String name) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name", name);
        return hjmallSystemConfigMapper.selectCountByExample(createExample(stringObjectMap)) > 0;
    }

    @Override
    public boolean exsitMenuName(String menuName) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("menuName", menuName);
        return hjmallSystemConfigMapper.selectCountByExample(createExample(stringObjectMap)) > 0;
    }


    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallSystemConfig.class);
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
            if (searchMap.get("module") != null && !"".equals(searchMap.get("module"))) {
                criteria.andEqualTo("module", searchMap.get("module"));
            }
        }
        return example;
    }
}
