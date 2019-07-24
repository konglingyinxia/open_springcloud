package com.kongling.platform.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.kongling.platform.entity.PO.AdminUser;
import com.kongling.platform.entity.VO.AdminUserVO;
import com.util.pageinfoutil.PageUtil;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
public interface IAdminUserService extends IService<AdminUser> {

    /**
     * 登录方法
     * @param account
     * @param password
     * @return
     */
    AdminUser selectByAccountLogin(String account, String password);

    /**
     * 分页查询管理员列表
     * @param adminUserVO
     * @param pageUtil
     * @return
     */
    PageInfo<AdminUserVO> selectByChoice(AdminUserVO adminUserVO, PageUtil pageUtil);

    /**
     * 禁用启用管理员
     * @param adminUserVO
     */
    void adminUserDisable(AdminUserVO adminUserVO);

    /**
     * 角色编辑
     * @param adminUserVO
     */
    void editUserRole(AdminUserVO adminUserVO);

    /**
     * 更新登陆时间
     *
     * @param id
     */
    void updateLoginTime(Long id);
}
