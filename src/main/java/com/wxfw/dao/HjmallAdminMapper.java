package com.wxfw.dao;

import com.wxfw.entity.HjmallAdmin;
import com.wxfw.entity.HjmallRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @program: hjmall
 * @description HjmallAdmin dao层
 * @author: Gaohw
 * @create: 2020-03-19 21:05
 **/
@Repository(value = "HjmallAdminMapper")
public interface HjmallAdminMapper extends Mapper<HjmallAdmin> {


    /**
     * 统计登录账号
     *
     * @param account
     * @return
     */
    int countByAccount(@Param("account") String account);

    /**
     * 根据账号ID查询角色信息
     *
     * @param adminId
     * @return
     */
    List<HjmallRole> findRolesByAdminId(@Param("adminId") String adminId);


    /**
     * 绑定shopId
     *
     * @param stringObjectMap
     */
    void bindShopId(Map<String, Object> stringObjectMap);
}
