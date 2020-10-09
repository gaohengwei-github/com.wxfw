package com.wxfw.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wxfw.dao.HjmallPermissionMapper;
import com.wxfw.entity.HjmallPermission;
import com.wxfw.model.HjmallPermissionVo;
import com.wxfw.util.Constant.Applications;
import com.wxfw.util.Constant.CurrentUser;
import com.wxfw.model.PermissionMetaVo;
import com.wxfw.util.redis.util.RedisUtil;
import com.wxfw.service.HjmallPermissionService;
import com.wxfw.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * HjmallPermissionServiceImpl
 *
 * @author gaohw
 * @date 2020/3/23
 */
@Service
public class HjmallPermissionServiceImpl implements HjmallPermissionService {

    @Autowired
    private HjmallPermissionMapper hjmallPermissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<HjmallPermission> findAllMenuByAdminId(String adminId) {
        List<HjmallPermission> hjmallPermissions = hjmallPermissionMapper.findAllMenuByAdminId(adminId);
        return (List<HjmallPermission>) buildTree(hjmallPermissions).get("content");
    }

    @Override
    public List<HjmallPermission> findAllPermissionByAdminId(String adminId) {
        return hjmallPermissionMapper.findAllPermissionByAdminId(adminId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HjmallPermission hjmallPermission) {
        hjmallPermission.setId(StrUtils.randomUUID());
        hjmallPermission.setAddTime(new Date());
        hjmallPermissionMapper.insert(hjmallPermission);
        getMenuTree(findByPid("0"));
        redisUtil.del("menu:*");
    }

    @Override
    public boolean exsitName(String name) {
        Map<String, Object> search = new HashMap<>();
        search.put("name", name);
        return !hjmallPermissionMapper.selectByExample(createExample(search)).isEmpty();
    }


    @Override
    public Map<String, Object> findAllPermissionList(String name, String addTime) {
        Map<String, Object> search = new HashMap<>();
        search.put("name", name);
        search.put("addTime", addTime);
        List<HjmallPermission> hjmallPermissions = hjmallPermissionMapper.selectByExample(createExample(search));
        return buildTree(hjmallPermissions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HjmallPermission hjmallPermission) {
        hjmallPermissionMapper.updateByPrimaryKey(hjmallPermission);
        getMenuTree(findByPid("0"));
        redisUtil.del("menu:*");
    }

    @Override
    public HjmallPermission findPermissionById(String id) {
        return hjmallPermissionMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<HjmallPermission> findByPid(String pid) {
        Map<String, Object> search = new HashMap<>();
        search.put("pid", pid);
        return hjmallPermissionMapper.selectByExample(createExample(search));
    }

    @Override
    public Object getMenuTree(List<HjmallPermission> hjmallPermissionList) {
        List<Map<String, Object>> list = new LinkedList<>();
        hjmallPermissionList.forEach(hjmallPermission -> {
                    if (hjmallPermission != null) {
                        if (!hjmallPermission.getStatus()) {
                            return;
                        }
                        Map<String, Object> search = new HashMap<>();
                        search.put("pid", hjmallPermission.getId());
                        List<HjmallPermission> menuList = hjmallPermissionMapper.selectByExample(createExample(search));
                        Map<String, Object> map = new HashMap<>(16);
                        map.put("id", hjmallPermission.getId());
                        map.put("label", hjmallPermission.getName());
                        if (menuList != null && menuList.size() != 0) {
                            map.put("children", getMenuTree(menuList));
                        }
                        list.add(map);
                    }
                }
        );
        CurrentUser currentUser = Applications.getCurrentUser();
        redisUtil.set("tree:" + currentUser.getUid(), list, 3600 * 2);
        return list;
    }

    /**
     * 构建菜单树
     *
     * @param hjmallPermissionList
     * @return
     */
    @Override
    public Map<String, Object> buildTree(List<HjmallPermission> hjmallPermissionList) {
        List<HjmallPermission> trees = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (HjmallPermission hjmallPermission : hjmallPermissionList) {
            if ("0".equals(hjmallPermission.getPid())) {
                trees.add(hjmallPermission);
            }
            for (HjmallPermission it : hjmallPermissionList) {
                if (hjmallPermission.getId().equals(it.getPid())) {
                    if (hjmallPermission.getChildren() == null) {
                        hjmallPermission.setChildren(new ArrayList<>());
                    }
                    hjmallPermission.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = hjmallPermissionList.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", StrUtils.isBlank(hjmallPermissionList) ? 0 : hjmallPermissionList.size());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<HjmallPermission> menuSet) {
        for (HjmallPermission hjmallPermission : menuSet) {
//            hjmallPermission.setStatus(false);
//            hjmallPermissionMapper.updateByPrimaryKey(hjmallPermission);
            hjmallPermissionMapper.delete(hjmallPermission);
        }
    }

    @Override
    public List<HjmallPermissionVo> buildMenus(List<HjmallPermission> hjmallPermissionList) {
        List<HjmallPermissionVo> list = new LinkedList<>();
        hjmallPermissionList.forEach(hjmallPermission -> {
                    if (hjmallPermission != null) {
                        List<HjmallPermission> hjmallPermissionChildren = hjmallPermission.getChildren();
                        HjmallPermissionVo permissionVo = new HjmallPermissionVo();
                        permissionVo.setName(hjmallPermission.getName());
                        // 一级目录需要加斜杠，不然会报警告
                        permissionVo.setPath("0".equals(hjmallPermission.getPid()) ? "/" + hjmallPermission.getPath() : hjmallPermission.getPath());
                        permissionVo.setHidden(hjmallPermission.getHidden());
                        if ("0".equals(hjmallPermission.getPid())) {
                            permissionVo.setComponent(StrUtil.isEmpty(hjmallPermission.getComponent()) ? "Layout" : hjmallPermission.getComponent());
                        } else if (!StrUtil.isEmpty(hjmallPermission.getComponent())) {
                            permissionVo.setComponent(hjmallPermission.getComponent());
                        }
                        permissionVo.setMeta(new PermissionMetaVo(hjmallPermission.getName(), hjmallPermission.getIcon()));
                        if (hjmallPermissionChildren != null && hjmallPermissionChildren.size() != 0) {
                            permissionVo.setAlwaysShow(true);
                            permissionVo.setRedirect("noredirect");
                            permissionVo.setChildren(buildMenus(hjmallPermissionChildren));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if ("0".equals(hjmallPermission.getPid())) {
                            HjmallPermissionVo hjmallPermissionVo = new HjmallPermissionVo();
                            hjmallPermissionVo.setMeta(permissionVo.getMeta());
                            // 非外链
                            hjmallPermissionVo.setPath("index");
                            hjmallPermissionVo.setName(permissionVo.getName());
                            hjmallPermissionVo.setComponent(permissionVo.getComponent());
                            permissionVo.setName(null);
                            permissionVo.setMeta(null);
                            permissionVo.setComponent("Layout");
                            List<HjmallPermissionVo> hjmallPermissionVoArrayList = new ArrayList<>();
                            hjmallPermissionVoArrayList.add(hjmallPermissionVo);
                            permissionVo.setChildren(hjmallPermissionVoArrayList);
                        }
                        list.add(permissionVo);
                    }
                }
        );
        CurrentUser currentUser = Applications.getCurrentUser();
        redisUtil.set("menu:" + currentUser.getUid(), list, 3600 * 2);
        return list;
    }


    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallPermission.class);
        example.setOrderByClause("sort ASC");
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 菜单名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andEqualTo("name", searchMap.get("name"));
            }
            if (searchMap.get("addTime") != null && !"".equals(searchMap.get("addTime"))) {
                String[] strings = searchMap.get("addTime").toString().split(",");
                criteria.andBetween("addTime", strings[0], strings[1]);
            }
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                criteria.andNotEqualTo("type", searchMap.get("type"));
            }
            // 组件地址
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            if (searchMap.get("pid") != null && !"".equals(searchMap.get("pid"))) {
                criteria.andEqualTo("pid", searchMap.get("pid"));
            }
        }
        return example;
    }
}
