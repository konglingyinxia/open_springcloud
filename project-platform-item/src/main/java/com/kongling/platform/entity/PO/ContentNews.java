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
 * 公司新闻
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ContentNews对象", description="公司新闻")
public class ContentNews extends Model<ContentNews> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "新闻标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "封面图")
    @TableField("img_cover")
    private String imgCover;

    @ApiModelProperty(value = "新闻来源")
    @TableField("source")
    private String source;

    @ApiModelProperty(value = "新闻内容")
    @TableField("details")
    private String details;

    @ApiModelProperty(value = "新闻类型 默认为0 方便以后扩展")
    @TableField("content_type")
    private Integer contentType;

    @ApiModelProperty(value = "是否展示 默认为1 展示")
    @TableField("is_show")
    private Boolean isShow;

    @ApiModelProperty(value = "是否置顶 默认为0 不置顶（推荐）")
    @TableField("is_top")
    private Boolean isTop;

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

    @TableId(value = "view_nums")
    @ApiModelProperty(value = "浏览量")
    private Long viewNums;


    /**
     * 订单开始日期、结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date endDate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
