package com.wxfw.service;

import com.wxfw.entity.HjmallRole;
import com.wxfw.model.HjmallRoleVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-24 19:26
 **/
public interface HjmallRoleService {

    /**
     * 查询角色列表
     *
     * @param page
     * @param size
     * @return
     */
    Object findAllRole(String roleName, int page, int size);

    /**
     * 查询有效的角色信息
     *
     * @param status
     * @return
     */
    List<HjmallRole> findRoleByStatus(boolean status);


    /**
     * 判断用户名是否存在
     *
     * @param roleName
     */
    boolean exsitByRoleName(String roleName);

    /**
     * 新增角色信息
     *
     * @param hjmallRole
     */
    void create(HjmallRole hjmallRole);

    /**
     * 编辑角色信息
     *
     * @param hjmallRole
     */
    void update(HjmallRole hjmallRole);


    /**
     * 根据主键查询角色信息
     *
     * @param id
     * @return
     */
    HjmallRole findRoleById(String id);


    /**
     * 数据模糊查询
     *
     * @param stringObjectMap
     * @return
     */
    HjmallRole findByParams(Map<String, Object> stringObjectMap);

    /**
     * 修改角色及菜单关联关系
     *
     * @param hjmallRoleVo
     */
    void updateMenu(HjmallRoleVo hjmallRoleVo);

    /**
     * 根据roleId查找管理员用户信息
     *
     * @param id
     * @return
     */
    int findByRoleId(String id);

    /**
     * 删除角色信息
     *
     * @param ids
     */
    void delete(Set<String> ids);
}
