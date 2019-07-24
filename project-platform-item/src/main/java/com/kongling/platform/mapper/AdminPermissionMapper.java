package com.kongling.platform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kongling.platform.entity.PO.AdminPermission;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单资源表 Mapper 接口
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
public interface AdminPermissionMapper extends BaseMapper<AdminPermission> {

    /**
     * 查询权限列表
     * @param adminPermissionVO
     * @return
     */
    List<AdminPermission> selectPermissionsBy(AdminPermissionVO adminPermissionVO);

    /**
     * 权限资源 递归查询
     *
     * @param roleId  角色 id
     * @param parentId 父级 id
     * @return
     */
    List<AdminPermissionRecListVO> getPermissionsRecAll(@Param("roleId") Long roleId, @Param("parentId") Long parentId);
}
