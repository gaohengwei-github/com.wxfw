package com.wxfw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallPermissionMapper;
import com.wxfw.dao.HjmallRoleMapper;
import com.wxfw.dao.RolePermissionRelationMapper;
import com.wxfw.entity.HjmallPermission;
import com.wxfw.entity.HjmallRole;
import com.wxfw.entity.RolePermissionRelation;
import com.wxfw.model.HjmallRoleVo;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallPermissionService;
import com.wxfw.service.HjmallRoleService;
import com.wxfw.util.PageUtil;
import com.wxfw.util.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * HjmallRoleServiceImpl
 *
 * @author gaohw
 * @date 2020/3/24
 */
@Service
public class HjmallRoleServiceImpl implements HjmallRoleService {

    @Autowired
    private HjmallRoleMapper hjmallRoleMapper;
    @Autowired
    private HjmallPermissionMapper hjmallPermissionMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;
    @Autowired
    private HjmallPermissionService hjmallPermissionService;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Object findAllRole(String roleName, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("blurry", roleName);
        List<HjmallRole> roles = hjmallRoleMapper.selectByExample(createExample(stringObjectMap));
        List<HjmallRoleVo> hjmallRoleVos = new ArrayList<>();
        for (HjmallRole hjmallRole : roles) {
            HjmallRoleVo hjmallRoleVo = new HjmallRoleVo();
            BeanUtils.copyProperties(hjmallRole, hjmallRoleVo);
            hjmallRoleVo.setPermissions(new HashSet(hjmallPermissionMapper.findAllByRoleId(hjmallRole.getId())));
            hjmallRoleVos.add(hjmallRoleVo);
        }
        PageInfo pageInfo = new PageInfo(hjmallRoleVos);
        return PageUtil.toPage(pageInfo);
    }

    @Override
    public List<HjmallRole> findRoleByStatus(boolean status) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("status", true);
        return hjmallRoleMapper.selectByExample(createExample(stringObjectMap));
    }

    @Override
    public boolean exsitByRoleName(String roleName) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("roleName", roleName);
        return !hjmallRoleMapper.selectByExample(createExample(stringObjectMap)).isEmpty();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HjmallRole hjmallRole) {
        hjmallRole.setId(StrUtils.randomUUID());
        hjmallRoleMapper.insert(hjmallRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        for (String string : ids) {
            hjmallRoleMapper.deleteByPrimaryKey(string);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HjmallRole hjmallRole) {
        hjmallRoleMapper.updateByPrimaryKey(hjmallRole);
    }

    @Override
    public HjmallRole findRoleById(String id) {
        return hjmallRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public HjmallRole findByParams(Map<String, Object> stringObjectMap) {
        List<HjmallRole> hjmallRoleList = hjmallRoleMapper.selectByExample(createExample(stringObjectMap));
        if (StrUtils.isEmpty(hjmallRoleList)) {
            return null;
        } else {
            return hjmallRoleList.get(0);
        }
    }

    @Override
    public int findByRoleId(String id) {
        return hjmallRoleMapper.findAllByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(HjmallRoleVo hjmallRoleVo) {
        rolePermissionRelationMapper.deleteByRoleId(hjmallRoleVo.getId());
        for (HjmallPermission hjmallPermission : hjmallRoleVo.getPermissions()) {
            RolePermissionRelation rolePermissionRelation = new
                    RolePermissionRelation(StrUtils.randomUUID(), hjmallRoleVo.getId(), hjmallPermission.getId());
            rolePermissionRelationMapper.insert(rolePermissionRelation);
        }
        // TODo 有待商榷 更新菜单的时机
        CurrentUser currentUser = Applications.getCurrentUser();
        List<HjmallPermission> permissions =
                (List<HjmallPermission>) hjmallPermissionService.buildTree
                        (hjmallPermissionMapper.findAllMenuByAdminId(currentUser.getUid())).get("content");
        hjmallPermissionService.buildMenus(permissions);

    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 组件地址
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            if (searchMap.get("roleName") != null && !"".equals(searchMap.get("roleName"))) {
                criteria.andEqualTo("roleName", searchMap.get("roleName"));
            }
            if (searchMap.get("blurry") != null && !"".equals(searchMap.get("blurry"))) {
                criteria.andLike("roleName", "%" + searchMap.get("blurry") + "%");
            }
        }
        return example;
    }
}
