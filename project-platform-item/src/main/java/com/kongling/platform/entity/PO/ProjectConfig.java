package com.kongling.platform.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目应用
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProjectConfig对象", description="项目应用")
public class ProjectConfig extends Model<ProjectConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "项目名字")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty(value = "应用描述")
    @TableField("project_describe")
    private String projectDescribe;

    @ApiModelProperty(value = "项目封面图")
    @TableField("cover_img")
    private String coverImg;

    @ApiModelProperty(value = "项目移动端地址")
    @TableField("project_webapp_url")
    private String projectWebappUrl;

    @ApiModelProperty(value = "项目管理后台地址")
    @TableField("project_admin_url")
    private String projectAdminUrl;

    @ApiModelProperty(value = "0:未禁用 1：禁用")
    @TableField("is_disable")
    private Boolean isDisable;

    @ApiModelProperty(value = "0:未删除 1:删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "项目类型id")
    @TableField("project_type_id")
    private Long projectTypeId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("timestamp")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @ApiModelProperty(value = "0:不展示 1:展示")
    @TableField("show_is")
    private Boolean showIs;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
