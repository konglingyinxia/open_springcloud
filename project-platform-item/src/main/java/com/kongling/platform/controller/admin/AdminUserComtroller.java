package com.kongling.platform.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.kongling.platform.entity.PO.AdminUser;
import com.kongling.platform.entity.PO.AdminUserRole;
import com.kongling.platform.entity.VO.AdminUserVO;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.kongling.platform.entity.VO.adminuser.AdminUserLoginInfo;
import com.kongling.platform.entity.VO.adminuser.AdminUserPwdVO;
import com.kongling.platform.service.IAdminPermissionService;
import com.kongling.platform.service.IAdminUserRoleService;
import com.kongling.platform.service.IAdminUserService;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import com.util.cache.UserCacheUtil;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import config.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author 卫星
 * @package com.suda.server.service.admin.controller
 * @date 2019-04-19  23:51
 * @project niuwan_cloud
 */
@RestController
@RequestMapping(value = "admin/user",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminUserComtroller {
    @Autowired
    private IAdminUserService adminUserService;
    @Autowired
    private UserCacheUtil userCacheUtil;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IAdminPermissionService adminPermissionService;
    @Autowired
    private IAdminUserRoleService adminUserRoleService;

    /**
     * 登陆请求
     *
     * @param account
     * @param password
     * @return
     */
    @LogMenthodName(name = "AOP环绕登陆请求")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String account, String password, HttpServletRequest req) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(account,password)){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUserVO = adminUserService .selectByAccountLogin(account,password);
        Long id=adminUserVO.getId();
        /**
         * 查询用户角色
         */
        AdminUserRole role = adminUserRoleService.getOne(new QueryWrapper<AdminUserRole>()
        .eq("user_id",id));
        Long roleId = 0L;
        if(role!= null) {
            roleId = role.getRoleId();
        }
        adminUserVO.setRoleId(roleId);
        String token = AuthSign.tokenSign(id, JSONObject.parseObject(JSONObject.toJSON(adminUserVO).toString()));
        //存储登陆token信息
        userCacheUtil.storeAdminUserLoginInfo(id,token);
        //存储登陆用户信息
        redisUtils.setStorageAdminUser(id,JSONObject.toJSON(adminUserVO).toString());
        /**
         * 设置sessionId
         */
        adminUserService.updateLoginTime(adminUserVO.getId());
        adminUserVO.setSessionId(token);
        AdminUserLoginInfo userLoginInfo = new AdminUserLoginInfo();
        BeanUtils.copyProperties(adminUserVO,userLoginInfo);
        return ResponseUtil.getSuccessMap(userLoginInfo);
    }

    /**
     * 获取用户信息
     */
    @LogMenthodName(name = "获取用户信息及权限")
    @RequestMapping(value = "/getAdminUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAdminUserInfo(AdminUser adminUser) throws UnsupportedEncodingException {
        if(adminUser.getId()==null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUser = adminUserService.getById(adminUser.getId());
        Long id=adminUser.getId();
        /**
         * 查询用户角色
         */
        AdminUserRole role = adminUserRoleService.getOne(new QueryWrapper<AdminUserRole>()
                .eq("user_id",id));
        Long roleId = 0L;
        if(role!= null) {
            roleId = role.getRoleId();
        }
        adminUser.setRoleId(roleId);
        /*
         *
         *用户权限信息
         */
        List<AdminPermissionRecListVO> permissions = adminPermissionService.getPermissionsRecAll(roleId);
        adminUser.setPermissionFirstMenuVOs(permissions);
        return ResponseUtil.getSuccessMap(adminUser);
    }




    /**
     * 管理员列表
     *
     */
    @RequestMapping(value = "/getAdminUsers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAdminUsers(AdminUserVO adminUserVO, HttpServletRequest req, PageUtil pageUtil) throws UnsupportedEncodingException {
        PageInfo<AdminUserVO> lists = adminUserService.selectByChoice(adminUserVO,pageUtil);
        return ResponseUtil.getSuccessMap(lists);
    }


    /**
     * 管理员禁用启用
     */
    @RequestMapping(value = "/adminUserDisable", method = RequestMethod.POST)
    @LogMenthodName(name = "禁用/启用管理员")
    @ResponseBody
    public Map<String, Object> adminUserDisable(AdminUserVO adminUserVO) throws UnsupportedEncodingException {
        if(adminUserVO.getId()==null
                || adminUserVO.getId() ==1
                || adminUserVO.getIsDisable() ==null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUserService.adminUserDisable(adminUserVO);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 更改管理员角色
     */
    @RequestMapping(value = "/editUserRole", method = RequestMethod.POST)
    @LogMenthodName(name = "更改管理员角色")
    @ResponseBody
    public Map<String, Object> editUserRole(AdminUserVO adminUserVO) throws UnsupportedEncodingException {
        if(adminUserVO.getId()==null
                || adminUserVO.getId() ==1
                || adminUserVO.getRoleId() ==null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUserService.editUserRole(adminUserVO);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 更改登陆账户账户密码
     */
    @RequestMapping(value = "/updateAdminPassword", method = RequestMethod.POST)
    @LogMenthodName(name = "更改账户密码")
    @ResponseBody
    public Map<String, Object> updateAdminPassword(AdminUserPwdVO adminUserVO) throws UnsupportedEncodingException {
        if(adminUserVO.getId()==null
                ){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUserSel = adminUserService.getById(adminUserVO.getId());
        if(adminUserSel == null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.NOUSER);
        }
        if(adminUserSel.getPassword().equalsIgnoreCase(adminUserVO.getNewPassword())){
            return ResponseUtil.getNotNormalMap(ResponseMsg.OLD_NEW_PASSWORD_NOT_EQUAL);
        }
        if(!adminUserSel.getPassword().equalsIgnoreCase(adminUserVO.getOldPassword())){
            return ResponseUtil.getNotNormalMap(ResponseMsg.OLD_PASSWORD_IS_ERROR);
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setPassword(adminUserVO.getNewPassword());
        adminUser.setId(adminUserVO.getId());
        adminUserService.updateById(adminUser);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 修改下属管理员密码
     */
    @RequestMapping(value = "/editAdminPassword", method = RequestMethod.POST)
    @LogMenthodName(name = "修改下属管理员密码")
    @ResponseBody
    public Map<String, Object> editAdminPassword(AdminUser adminUserVO) throws UnsupportedEncodingException {
        if (adminUserVO.getId() == null
        || StringUtils.isBlank(adminUserVO.getPassword())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminUserVO.getId());
        adminUser.setPassword(adminUserVO.getPassword());
        adminUserService.updateById(adminUser);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 添加管理员
     */
    @RequestMapping(value = "/addAdminUser", method = RequestMethod.POST)
    @LogMenthodName(name = "添加管理员")
    @ResponseBody
    public Map<String, Object> addAdminUser(AdminUser vo) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(vo.getPassword(),vo.getAccount())){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser user = adminUserService.getOne(new QueryWrapper<AdminUser>()
        .eq("account",vo.getAccount())
        .eq("is_deleted",0));
        if(user!=null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.USER_HAS_EXIST);
        }
        adminUserService.save(vo);
        return ResponseUtil.getSuccessMap();
    }


    /**
     * 删除管理员
     */
    @RequestMapping(value = "/delAdminUser", method = RequestMethod.POST)
    @LogMenthodName(name = "删除管理员")
    @ResponseBody
    public Map<String, Object> delAdminUser(AdminUser vo) throws UnsupportedEncodingException {
        if(vo.getId()==null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ID_IS_EMPTY);
        }
        adminUserService.update(new UpdateWrapper<AdminUser>()
                .set("is_deleted",1)
        .eq("id",vo.getId())
        .ne("id",1));
        return ResponseUtil.getSuccessMap();
    }




}
