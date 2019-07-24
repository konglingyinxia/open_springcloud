package com.kongling.platform.entity.VO.adminuser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class AdminUserLoginInfo extends Model<AdminUserLoginInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @TableField(exist = false)
    private String sessionId;



}
