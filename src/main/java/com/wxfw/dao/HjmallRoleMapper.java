package com.wxfw.dao;

import com.wxfw.entity.HjmallRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-24 19:39
 **/
@Repository(value = "HjmallRoleMapper")
public interface HjmallRoleMapper extends Mapper<HjmallRole> {

    /**
     * 根据角色查找admin
     *
     * @param roleId
     * @return
     */
    int findAllByRoleId(@Param("roleId") String roleId);
}
