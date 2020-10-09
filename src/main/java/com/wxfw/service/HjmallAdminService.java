package com.wxfw.service;

import com.wxfw.entity.HjmallAdmin;
import com.wxfw.entity.HjmallRole;
import com.wxfw.model.HjmallAdminVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: hjmall
 * @description
 * @author: Gaohw
 * @create: 2020-03-19 21:00
 **/
public interface HjmallAdminService {


    /**
     * 判断当前用户名是否存在
     *
     * @param account
     * @return
     */
    boolean accountExist(String account);

    /**
     * 判断邮箱是否存在
     *
     * @param email
     * @return
     */
    boolean emailExist(String email);

    /**
     * 判断电话是否存在
     *
     * @param phone
     * @return
     */
    boolean phoneExist(String phone);

    /**
     * 注册用户
     *
     * @param hjmallAdmin
     * @return
     */
    void regist(HjmallAdminVo hjmallAdmin);

    /**
     * 管理端修改账户信息
     *
     * @param hjmallAdmin
     */
    void update(HjmallAdminVo hjmallAdmin);

    /**
     * 个人中心 修改个人信息
     *
     * @param hjmallAdmin
     */
    void updateCenter(HjmallAdmin hjmallAdmin);

    /**
     * @param ids
     */
    void delete(Set<String> ids);

    /**
     * 通过账号或者手机号码登陆
     *
     * @param account 账号或邮箱
     * @param pwd
     * @return
     */
    HjmallAdmin login(String account, String pwd);


    HjmallAdmin findByAccount(String account);

    /**
     * 根据adminId查询
     *
     * @param adminId
     * @return
     */
    List<HjmallRole> findRolesByAdminId(String adminId);

    /**
     * 根据主键查询管理员账号信息
     *
     * @param id
     * @return
     */
    HjmallAdmin findAdminById(String id);

    /**
     * 修改用户头像
     *
     * @param file
     */
    void updateAvatar(MultipartFile file) throws IOException;

    /**
     * 查询用户列表
     *
     * @param blurry
     * @param createTime
     * @param page
     * @param size
     * @return
     */
    Object findAllAdmin(String blurry, String createTime, int page, int size);

    /**
     * 绑定shopId
     *
     * @param stringObjectMap
     */
    void bindShopId(Map<String, Object> stringObjectMap);
}
