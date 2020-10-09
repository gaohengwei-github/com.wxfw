package com.wxfw.service;

import com.wxfw.entity.HjmallPermission;
import com.wxfw.model.HjmallPermissionVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-23 21:18
 **/
public interface HjmallPermissionService {

    /**
     * 根据角色查询菜单信息
     *
     * @param adminId
     * @return
     */
    List<HjmallPermission> findAllMenuByAdminId(String adminId);


    /**
     * 根据角色查询菜单信息
     *
     * @param adminId
     * @return
     */
    List<HjmallPermission> findAllPermissionByAdminId(String adminId);

    /**
     * 新增
     *
     * @param hjmallPermission
     */
    void create(HjmallPermission hjmallPermission);

    /**
     * 菜单名字是否存在
     *
     * @param name
     * @return
     */
    boolean exsitName(String name);


    /**
     * 修改菜单信息
     *
     * @param hjmallPermission
     */
    void update(HjmallPermission hjmallPermission);

    /**
     * 根据主键查询菜单信息
     *
     * @param id
     * @return
     */
    HjmallPermission findPermissionById(String id);

    /**
     * 构建菜单显示路由
     *
     * @param hjmallPermissionList
     * @return
     */
    List<HjmallPermissionVo> buildMenus(List<HjmallPermission> hjmallPermissionList);

    /**
     * 获取全部菜单列表
     *
     * @param name
     * @return
     */
    Map<String, Object> findAllPermissionList(String name, String addTime);

    /**
     * 获取菜单树
     *
     * @param menus
     * @return
     */
    Object getMenuTree(List<HjmallPermission> menus);

    /**
     * 根据父ID查询菜单信息
     *
     * @param pid
     * @return
     */
    List<HjmallPermission> findByPid(String pid);

    /**
     * 构建菜单树
     *
     * @param hjmallPermissionList
     * @return
     */
    Map<String, Object> buildTree(List<HjmallPermission> hjmallPermissionList);

    /**
     * 删除菜单信息
     *
     * @param menuSet
     */
    void delete(Set<HjmallPermission> menuSet);
}
