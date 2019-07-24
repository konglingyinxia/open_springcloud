package com.kongling.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kongling.platform.entity.PO.AdminPermission;
import com.kongling.platform.entity.PO.AdminRole;
import com.kongling.platform.entity.PO.AdminUserRole;
import com.kongling.platform.mapper.*;
import com.kongling.platform.service.IAdminRoleService;
import com.util.StringUtils;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.advice.CommonException;
import config.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-05
 */
@Service
@Slf4j
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

    @Autowired
    private  AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;
    @Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;


    /**
     * 分页查询角色
     * @param adminRole
     * @param pageUtil
     * @return
     */
    @Override
    public PageInfo<AdminRole> selectRoles(AdminRole adminRole, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<AdminRole> list = adminRoleMapper.selectRoles(adminRole);
        return  new PageInfo<>(list);
    }

    /**
     * 添加角色
     *
     * @param adminRole
     */
    @Override
    public void addRole(AdminRole adminRole) {
        /**
         *  查询角色是否存在
         */
        List<AdminRole> lists = adminRoleMapper.selectRoles(adminRole);
        if(lists.size()>0){
            throw new CommonException(ResponseMsg.ROLE_HAVE_EXIST);
        }
        adminRole.setCreateTime(new Date());
        adminRole.setRoleValue(StringUtils.getUUID());
        adminRoleMapper.insert(adminRole);
    }
    /**
     * 删除角色
     *
     * @param adminRole
     */
    @Override
    @Transactional(rollbackFor = {})
    public void delRole(AdminRole adminRole) {
        /**
         *  查询是否有用户占用该角色
         */
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("role_id",adminRole.getId());

        List<AdminUserRole> lists = adminUserRoleMapper.selectByMap(resultMap);
        if(lists.size()>0){
            throw new CommonException(ResponseMsg.ROLE_HAVE_OCCUPY_NO_DEL);
        }
        adminRoleMapper.deleteById(adminRole.getId());
        //删除权限
        adminRolePermissionMapper.deleteByMap(resultMap);

    }
    @Autowired
    RedisUtils redisUtils;
    /**
     * 角色重新授权
     * @param roleId
     * @param permissionIds
     */

    @Override
    @Transactional(rollbackFor = {})
    public void addRolePermission(Long roleId, String permissionIds) {
        List<Long> permissionIdsArry =(List<Long>)(Object) Arrays.asList(permissionIds.split(",|，"));
        /**
         *  查询是否有用户占用该角色
         */
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("role_id",roleId);
        adminRolePermissionMapper.deleteByMap(resultMap);
        int  insertNum = adminRolePermissionMapper.insertLists(roleId,permissionIdsArry);
        taskExecutor.execute(()->{
            try {
                //查询本次角色更新权限
                List<AdminPermission> permissions = adminPermissionMapper.selectList(
                        new QueryWrapper<AdminPermission>()
                .in("id",permissionIdsArry));
                for(AdminPermission permission:permissions){
                    redisUtils.setStorageRolePermission(roleId,permission.getMenuUrl());
                }
            }catch (Exception e){
                log.error("角色授权错误信息：\n"+ ExceptionUtils.getFullStackTrace(e));
            }
        });

    }

    /**
     * 角色编辑
     * @param adminRole
     */
    @Override
    public void updateRole(AdminRole adminRole) {
        /**
         *  查询角色是否存在
         */
        List<AdminRole> lists = adminRoleMapper.selectRoles(adminRole);
        if(lists.size()>1){
            throw new CommonException(ResponseMsg.ROLE_HAVE_EXIST);
        }
        adminRole.setAdminUserId(null);
        adminRole.setRoleValue(null);
        adminRoleMapper.updateById(adminRole);
    }
}
