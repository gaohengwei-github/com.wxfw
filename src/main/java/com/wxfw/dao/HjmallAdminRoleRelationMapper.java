package com.wxfw.dao;

import com.wxfw.entity.AdminRoleRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-04-04 15:53
 **/
@Repository(value = "HjmallAdminRoleRelationMapper")
public interface HjmallAdminRoleRelationMapper extends Mapper<AdminRoleRelation> {

    void deleteAllByAdminId(@Param("adminId") String adminId);
}
