package com.kongling.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kongling.platform.entity.PO.AdminRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
public interface AdminRolePermissionMapper extends BaseMapper<AdminRolePermission> {

    /**
     * 批量插入
     *
     * @param roleId
     * @param permissionIdsArry
     */
    int insertLists(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIdsArry);
}
