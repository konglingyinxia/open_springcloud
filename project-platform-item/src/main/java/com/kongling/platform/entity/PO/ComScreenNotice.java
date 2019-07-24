package com.kongling.platform.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 弹屏公告
 * </p>
 *
 * @author 卫星
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ComScreenNotice对象", description="弹屏公告")
public class ComScreenNotice extends Model<ComScreenNotice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("tile")
    private String tile;

    @ApiModelProperty(value = "公告内容")
    @TableField("details")
    private String details;

    @TableField("timestamp")
    private Date timestamp;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
