package com.wxfw.dao;


import com.wxfw.entity.HjmallPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-23 20:44
 **/
@Repository(value = "HjmallPermissionMapper")
public interface HjmallPermissionMapper extends Mapper<HjmallPermission> {

    /**
     * 根据adminId查询菜单信息
     *
     * @return
     */
    List<HjmallPermission> findAllMenuByAdminId(@Param("adminId") String adminId);

    /**
     * 根据adminId查询权限信息
     *
     * @return
     */
    List<HjmallPermission> findAllPermissionByAdminId(@Param("adminId") String adminId);


    /**
     * 根据roleId查询权限信息
     *
     * @param roleId
     * @return
     */
    List<HjmallPermission> findAllByRoleId(@Param("roleId") String roleId);


}
