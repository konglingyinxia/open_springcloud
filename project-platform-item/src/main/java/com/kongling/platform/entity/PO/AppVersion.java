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
 *
 * </p>
 *
 * @author kongling
 * @since 2019-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AppVersion对象", description="")
public class AppVersion extends Model<AppVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "升级标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "版本号")
    @TableField("app_version")
    private String appVersion;

    @ApiModelProperty(value = "内容")
    @TableField("details")
    private String details;

    @ApiModelProperty(value = "下载地址")
    @TableField("down_url")
    private String downUrl;

    @ApiModelProperty(value = "系统类型  1:ios 2:android 3:pc")
    @TableField("app_type")
    private Byte appType;

    @ApiModelProperty(value = "是否强制更新  0:不强更  1:强更")
    @TableField("is_forced_update")
    private Byte isForcedUpdate;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "1:删除，0:未删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("timestamp")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @ApiModelProperty(value = "版本小号")
    @TableField("app_version_small")
    private String appVersionSmall;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
