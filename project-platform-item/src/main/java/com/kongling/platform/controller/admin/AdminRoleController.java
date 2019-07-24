package com.kongling.platform.controller.admin;

import com.github.pagehelper.PageInfo;
import com.kongling.platform.entity.PO.AdminRole;
import com.kongling.platform.entity.PO.AdminRolePermission;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.kongling.platform.service.IAdminPermissionService;
import com.kongling.platform.service.IAdminRoleService;
import com.util.StringUtils;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.advice.CommonException;
import config.annotation.LogMenthodName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 角色管理控制层
 *
 * @author kongling
 * @package com.suda.server.service.admin.controller
 * @date 2019-05-05  15:31
 * @project niuwan_cloud
 */
@RestController
@RequestMapping(value = "admin/role",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminRoleController {
    @Autowired
    private IAdminRoleService adminRoleService;
    /**
     * 角色列表查看
     */
    @RequestMapping(value = "/getRoles")
    @ResponseBody
    public Map<String, Object> getRoles(AdminRole adminRole, PageUtil pageUtil) {
        PageInfo<AdminRole> lists = adminRoleService.selectRoles(adminRole,pageUtil);
        return ResponseUtil.getSuccessMap(lists);
    }

    /**
     * 角色编辑
     * 编辑名字
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "编辑角色")
    public Map<String, Object> updateRole(AdminRole adminRole) {
        if(adminRole.getId() ==null || StringUtils.isBlank(adminRole.getRoleName())){
            throw new CommonException(ResponseMsg.ERROR_PARAM);
        }
        adminRoleService.updateRole(adminRole);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 角色添加
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "添加角色")
    public Map<String, Object> addRole(AdminRole adminRole) {
        if(StringUtils.isBlank(adminRole.getRoleName())|| adminRole.getAdminUserId() ==null ){
            throw new CommonException(ResponseMsg.ERROR_PARAM);
        }
        adminRoleService.addRole(adminRole);
        return ResponseUtil.getSuccessMap();
    }
    /**
     * 角色删除
     */
    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delRole(AdminRole adminRole) {
        if(adminRole.getId() ==null ){
            throw new CommonException(ResponseMsg.ID_IS_EMPTY);
        }
        adminRoleService.delRole(adminRole);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 角色授权
     */
    @RequestMapping(value = "/addRolePermission", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRolePermission(Long roleId,String permissionIds) {
        if(roleId ==null || StringUtils.isBlank(permissionIds)){
            throw new CommonException(ResponseMsg.ID_IS_EMPTY);
        }
        adminRoleService.addRolePermission(roleId,permissionIds);
        return ResponseUtil.getSuccessMap();
    }

    @Autowired
    private IAdminPermissionService adminPermissionService;

    /**
     * 角色权限 递归查询
     */
    @RequestMapping(value = "/getPermissionsRecAll")
    @ResponseBody
    public Map<String, Object> getPermissionsRecAll(AdminRolePermission rolePermission) {
        List<AdminPermissionRecListVO> lists = adminPermissionService.getPermissionsRecAll(rolePermission.getRoleId());
        return ResponseUtil.getSuccessMap(lists);
    }



}
