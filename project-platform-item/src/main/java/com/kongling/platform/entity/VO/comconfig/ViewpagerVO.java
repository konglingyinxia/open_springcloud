package com.kongling.platform.entity.VO.comconfig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * <p>
 * 轮播图配置
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@ApiModel(value="ComConfigViewpager对象", description="轮播图配置")
@ToString
public class ViewpagerVO extends Model<ViewpagerVO> {


    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "图片类型：1.app 2.pc")
    @TableField("type")
    private Integer type;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
