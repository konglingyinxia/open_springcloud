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
 * 客服设置
 * </p>
 *
 * @author kongling
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ComConfigService对象", description="客服设置")
public class ComConfigService extends Model<ComConfigService> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "客服账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "二维码路径")
    @TableField("qr_code_url")
    private String qrCodeUrl;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("timestamp")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @ApiModelProperty(value = "客服类型1，qq 2，微信 3，邮箱 ")
    @TableField("type")
    private Integer type;
    @ApiModelProperty(value = "客服类型1，qq 2，微信 3，邮箱")
    @TableField("type_name")
    private String typeName;
    @ApiModelProperty(value = "logo图片")
    @TableField("logo_img")
    private String logoImg;




    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
