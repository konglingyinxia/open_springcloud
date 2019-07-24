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

/**
 * <p>
 * 平台钱包账号配置
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ComConfigPlatformWallet对象", description="平台钱包账号配置")
public class ComConfigPlatformWallet extends Model<ComConfigPlatformWallet> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "币种编码")
    @TableField("stock_code")
    private String stockCode;

    @ApiModelProperty(value = "远程调用用户名")
    @TableField("rpc_allow_username")
    private String rpcAllowUsername;

    @ApiModelProperty(value = "远程调用密码")
    @TableField("rpc_allow_password")
    private String rpcAllowPassword;

    @ApiModelProperty(value = "远程调用ip")
    @TableField("rpc_allow_ip")
    private String rpcAllowIp;

    @ApiModelProperty(value = "远程调用端口")
    @TableField("rpc_allow_port")
    private String rpcAllowPort;

    @ApiModelProperty(value = "钱包密码")
    @TableField("wallet_password")
    private String walletPassword;

    @ApiModelProperty(value = "钱包地址")
    @TableField("wallet_addr")
    private String walletAddr;

    @ApiModelProperty(value = "钱包密钥")
    @TableField("wallet_secret_key")
    private String walletSecretKey;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
