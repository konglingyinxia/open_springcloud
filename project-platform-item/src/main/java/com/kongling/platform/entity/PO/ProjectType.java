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
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProjectType对象", description="")
public class ProjectType extends Model<ProjectType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型名字")
    @TableField("type_name")
    private String typeName;

    @ApiModelProperty(value = "类型描述")
    @TableField("type_describe")
    private String typeDescribe;

    @ApiModelProperty(value = "应用数量")
    @TableField("project_num")
    private Long projectNum;

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

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
