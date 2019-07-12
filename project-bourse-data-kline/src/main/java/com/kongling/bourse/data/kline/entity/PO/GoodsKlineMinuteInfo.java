package com.kongling.bourse.data.kline.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.format.CustomBigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品分时信息
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GoodsKlineMinuteInfo对象", description="商品分时信息")
public class GoodsKlineMinuteInfo extends Model<GoodsKlineMinuteInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "代码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "期数 20180606")
    @TableField("period")
    private String period;

    @ApiModelProperty(value = "成交量")
    @TableField("volume")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal volume;

    @ApiModelProperty(value = "当前价")
    @TableField("price")
    private String price;

    @ApiModelProperty(value = "前一刻开盘价")
    @TableField("opening_price")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal openPrice;

    @ApiModelProperty(value = "前一刻收盘价")
    @TableField("closing_price")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal closePrice;

    @ApiModelProperty(value = "昨收盘价")
    @TableField("pre_closing_price")
    private String prevClose;

    @ApiModelProperty(value = "最高价")
    @TableField("highest_price")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal high;

    @ApiModelProperty(value = "最低价")
    @TableField("lowest_price")
    private BigDecimal low;

    @ApiModelProperty(value = "日期")
    @TableField("date_ymd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateYmd;

    @ApiModelProperty(value = "日期时间")
    @TableField("date")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
