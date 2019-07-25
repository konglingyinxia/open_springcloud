package com.kongling.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.kongling.platform.entity.PO.AdminPermission;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionVO;
import com.kongling.platform.entity.VO.RolePermission.RoleMenuVO;
import com.util.pageinfoutil.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单资源表 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
public interface IAdminPermissionService extends IService<AdminPermission> {

    /**
     * 菜单权限列表
     * @param adminPermissionVO
     * @param pageUtil
     * @return
     */
    PageInfo<AdminPermission> selectPermissionsBy(AdminPermissionVO adminPermissionVO, PageUtil pageUtil);

    /**
     * 删除权限资源
     * @param adminPermission
     */
    void delPermissionsById(AdminPermission adminPermission);


    /**
     * 权限资源 递归查询
     *
     * @return
     */
    List<AdminPermissionRecListVO> getPermissionsRecAll(Long roleId);

    /**
     * 获取vue 菜单路由信息
     * @param roleMenuVOS
     * @param lists
     * @return
     */
    List<RoleMenuVO> selectRouters(ArrayList<RoleMenuVO> roleMenuVOS, List<AdminPermissionRecListVO> lists,Long roleId);
}
