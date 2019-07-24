package com.kongling.platform.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kongling.platform.entity.VO.RolePermission.AdminPermissionRecListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@ApiModel(value="AdminUser对象", description="")
@Data
public class AdminUser extends Model<AdminUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "添加时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long createTime;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @TableField("timestamp")
    private Date timestamp;

    @ApiModelProperty(value = "其他状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "0:有效 1:禁止登录")
    @TableField("is_disable")
    private Boolean isDisable;

    @ApiModelProperty(value = "1:删除，0:未删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty(value = "父级管理员id")
    @TableField("admin_parent_id")
    private Long adminParentId;

    @ApiModelProperty(value = "父级分销代理商id")
    @TableField("agent_parent_id")
    private Long agentParentId;

    @ApiModelProperty(value = "代理商邀请码")
    @TableField("user_invite_code")
    private String userInviteCode;

    @ApiModelProperty(value = "推荐邀请关系树")
    @TableField("invitation_path")
    private String invitationPath;

    @ApiModelProperty(value = "审核状态(1-待审核;2-已审核;3-拒绝)")
    @TableField("inspect_status")
    private Integer inspectStatus;

    @TableField(exist = false)
    private String sessionId;

    /**
     * 用户权限信息
     */
    @TableField(exist = false)
    private List<AdminPermissionRecListVO>  permissionFirstMenuVOs;

    /**
     * 用户角色 id
     */
    @ApiModelProperty(value = "角色id")
    @TableField(exist = false)
    private Long roleId;


}
