package com.kongling.platform.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@ApiModel(value="ComConfigSys对象", description="系统配置")
public class ComConfigSys extends Model<ComConfigSys> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "配置的key键名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "配置的val值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "配置分组(1-网站信息;2-网站协议;3-法币交易;4-币币交易;5-杠杆交易;6-短信设置;7-充/提设置;8-平台账户,9-关于我们,10-APP轮播图,11-集团信息，12-抽奖奖项/中奖比例,13-团队奖条件，14-团队奖收益)")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "备注信息")
    @TableField("name_notes")
    private String nameNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getNameNotes() {
        return nameNotes;
    }

    public void setNameNotes(String nameNotes) {
        this.nameNotes = nameNotes;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ComConfigSys{" +
        "id=" + id +
        ", name=" + name +
        ", value=" + value +
        ", type=" + type +
        ", nameNotes=" + nameNotes +
        "}";
    }
}
