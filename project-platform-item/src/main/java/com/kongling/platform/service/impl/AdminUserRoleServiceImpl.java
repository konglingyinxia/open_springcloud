package com.kongling.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kongling.platform.entity.PO.AdminUserRole;
import com.kongling.platform.mapper.AdminUserRoleMapper;
import com.kongling.platform.service.IAdminUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色用户关联表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {

}
