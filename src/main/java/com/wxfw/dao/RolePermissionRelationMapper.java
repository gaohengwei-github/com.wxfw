package com.wxfw.dao;

import com.wxfw.entity.RolePermissionRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-28 17:37
 **/
@Repository(value = "RolePermissionRelationMapper")
public interface RolePermissionRelationMapper extends Mapper<RolePermissionRelation> {

    /**
     * 根据roleId删除权限菜单关联信息
     *
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") String roleId);
}
