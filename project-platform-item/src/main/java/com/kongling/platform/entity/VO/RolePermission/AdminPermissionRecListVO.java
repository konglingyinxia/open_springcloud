package com.kongling.platform.entity.VO.RolePermission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单资源表 递归资源信息
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AdminPermission对象", description="菜单资源表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminPermissionRecListVO extends Model<AdminPermissionRecListVO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单或按钮名字")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    @TableField("menu_url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单样式")
    @TableField("menu_class")
    private String menuClass;

    @ApiModelProperty(value = "菜单类型  1 菜单  2按钮")
    @TableField("menu_type")
    private Integer menuType;

    @ApiModelProperty(value = "上级菜单")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "排序")
    @TableField("menu_order")
    private Integer menuOrder;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "变化时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    /**
     * 递归子级菜单
     */
    List<AdminPermissionRecListVO> children;

    /**
     * 是否被选中，角色使用
     */
    @TableField("checked")
    private Boolean checked;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
