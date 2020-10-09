package com.wxfw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxfw.dao.HjmallAdminMapper;
import com.wxfw.dao.HjmallAdminRoleRelationMapper;
import com.wxfw.entity.AdminRoleRelation;
import com.wxfw.entity.HjmallAdmin;
import com.wxfw.entity.HjmallRole;
import com.wxfw.model.HjmallAdminVo;
import com.wxfw.service.HjmallAdminService;
import com.wxfw.util.MD5;
import com.wxfw.util.PageUtil;
import com.wxfw.util.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.*;

/**
 * HjmallAdminServiceImpl
 *
 * @author gaohw
 * @date 2020/3/19
 */
@Service
public class HjmallAdminServiceImpl implements HjmallAdminService {

    @Autowired
    private HjmallAdminMapper hjmallAdminMapper;
    @Autowired
    private HjmallAdminRoleRelationMapper hjmallAdminRoleRelationMapper;




    @Override
    public boolean accountExist(String account) {
        int count = hjmallAdminMapper.countByAccount(account);
        return count >= 1;
    }

    @Override
    public HjmallAdmin findByAccount(String account) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("account", account);
        return hjmallAdminMapper.selectOneByExample(createExample(searchMap));
    }

    @Override
    public boolean emailExist(String email) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("email", email);
        return hjmallAdminMapper.selectCountByExample(createExample(searchMap)) >= 1;
    }

    @Override
    public boolean phoneExist(String phone) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("phoneNumber", phone);
        return hjmallAdminMapper.selectCountByExample(createExample(searchMap)) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HjmallAdminVo hjmallAdminVo) {
        HjmallAdmin hjmallAdmin = new HjmallAdmin();
        BeanUtils.copyProperties(hjmallAdminVo, hjmallAdmin);
        hjmallAdminMapper.updateByPrimaryKey(hjmallAdmin);
        hjmallAdminRoleRelationMapper.deleteAllByAdminId(hjmallAdminVo.getId());
        Set<HjmallRole> hjmallRoles = hjmallAdminVo.getRoles();
        for (HjmallRole hjmallRole : hjmallRoles) {
            AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
            adminRoleRelation.setAdminId(hjmallAdminVo.getId());
            adminRoleRelation.setRoleId(hjmallRole.getId());
            adminRoleRelation.setId(StrUtils.randomUUID());
            hjmallAdminRoleRelationMapper.insertSelective(adminRoleRelation);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(HjmallAdmin hjmallAdmin) {
        hjmallAdmin.setPwd(MD5.toMD5(hjmallAdmin.getPwd()));
        hjmallAdminMapper.updateByPrimaryKey(hjmallAdmin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        for (String id : ids) {
            hjmallAdminMapper.deleteByPrimaryKey(id);
//            HjmallAdmin hjmallAdmin = hjmallAdminMapper.selectByPrimaryKey(id);
//            hjmallAdmin.setStatus(false);
//            hjmallAdminMapper.updateByPrimaryKey(hjmallAdmin);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regist(HjmallAdminVo hjmallAdminVo) {
        HjmallAdmin hjmallAdmin = new HjmallAdmin();
        BeanUtils.copyProperties(hjmallAdminVo, hjmallAdmin);
        String adminId = StrUtils.randomUUID();
        hjmallAdmin.setId(adminId);
        hjmallAdmin.setPwd(MD5.toMD5("123456"));
        hjmallAdmin.setAddTime(new Date());
        hjmallAdminMapper.insert(hjmallAdmin);
        Set<HjmallRole> hjmallRoles = hjmallAdminVo.getRoles();
        for (HjmallRole hjmallRole : hjmallRoles) {
            AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
            adminRoleRelation.setAdminId(adminId);
            adminRoleRelation.setRoleId(hjmallRole.getId());
            adminRoleRelation.setId(StrUtils.randomUUID());
            hjmallAdminRoleRelationMapper.insertSelective(adminRoleRelation);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) throws IOException {
//        //调用腾讯云工具上传文件
//        String fileName = hjmallTencentossConfigService.upload(multipartFile);
//        //程序结束时，删除临时文件
//        HjmallAdmin hjmallAdmin = hjmallAdminMapper.selectByPrimaryKey(Applications.getCurrentUser().getUid());
//        hjmallAdmin.setIcon(fileName);
//        hjmallAdminMapper.updateByPrimaryKey(hjmallAdmin);
    }


    @Override
    public HjmallAdmin login(String account, String pwd) {
        Map<String, Object> searchMap = new HashMap<>();
        if (!StrUtils.isBlank(pwd)) {
            searchMap.put("pwd", MD5.toMD5(pwd));
        }
        searchMap.put("account", account);
        ;
        searchMap.put("status", true);
        Example example = createExample(searchMap);
        return hjmallAdminMapper.selectOneByExample(example);
    }


    @Override
    public List<HjmallRole> findRolesByAdminId(String adminId) {
        // hjmallAdminMapper.deleteAdminRoleRelationByTime();
        return hjmallAdminMapper.findRolesByAdminId(adminId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindShopId(Map<String, Object> stringObjectMap) {
        hjmallAdminMapper.bindShopId(stringObjectMap);
    }

    @Override
    public HjmallAdmin findAdminById(String id) {
        return hjmallAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public Object findAllAdmin(String blurry, String createTime, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("blurry", blurry);
        stringObjectMap.put("addTime", createTime);
        List<HjmallAdmin> adminList = hjmallAdminMapper.selectByExample(createExample(stringObjectMap));
        List<HjmallAdminVo> hjmallAdminVos = new ArrayList<>();
        for (HjmallAdmin hjmallAdmin : adminList) {
            HjmallAdminVo hjmallAdminVo = new HjmallAdminVo();
            BeanUtils.copyProperties(hjmallAdmin, hjmallAdminVo);
            hjmallAdminVo.setRoles(new HashSet<>(findRolesByAdminId(hjmallAdmin.getId())));
            hjmallAdminVos.add(hjmallAdminVo);
        }
        PageInfo pageInfo = new PageInfo(hjmallAdminVos);
        return PageUtil.toPage(pageInfo);
    }

    /**
     * 构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(HjmallAdmin.class);
        example.setOrderByClause("add_time DESC");
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键id
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 登录账号
            if (searchMap.get("account") != null && !"".equals(searchMap.get("account"))) {
                criteria.andEqualTo("account", searchMap.get("account")).
                        orEqualTo("phoneNumber", searchMap.get("account")).
                        orEqualTo("email", searchMap.get("account"));
            }
            if (searchMap.get("blurry") != null && !"".equals(searchMap.get("blurry"))) {
                criteria.andLike("account", "%" + searchMap.get("blurry") + "%").
                        orLike("phoneNumber", "%" + searchMap.get("blurry") + "%").
                        orLike("email", "%" + searchMap.get("blurry") + "%");
            }
            // 手机号码
            if (searchMap.get("phoneNumber") != null && !"".equals(searchMap.get("phoneNumber"))) {
                criteria.andEqualTo("phoneNumber", searchMap.get("phoneNumber"));
            }
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                criteria.andEqualTo("email", searchMap.get("email"));
            }
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            // 账号密码
            if (searchMap.get("pwd") != null && !"".equals(searchMap.get("pwd"))) {
                criteria.andEqualTo("pwd", searchMap.get("pwd"));
            }
            if (searchMap.get("addTime") != null && !"".equals(searchMap.get("addTime"))) {
                String[] strings = searchMap.get("addTime").toString().split(",");
                criteria.andBetween("addTime", strings[0], strings[1]);
            }

        }
        return example;
    }

}
