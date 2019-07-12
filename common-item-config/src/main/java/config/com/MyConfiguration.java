package config.com;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 卫星
 * @package config.com
 * @date 2019-04-19  23:05
 * @project niuwan_cloud
 */
@ConfigurationProperties(prefix="my-configuration")
@Data
@Component
public class MyConfiguration {

    //上传路径
    private String uploadPath;
    //访问路径
    private String imagePath;

    //session 存储时间

    private Integer sessionTimeout;
    /*************** 阿里云信息 **************************/
    /**
     * LTAI2KhhGGvbXzUZ
     * 阿里key
     */
    private String  accessKeyId="LTAImOWJOk8m1LO7";
    /**
     * n7islklEHqVefD4DR6oNp3Kz5h6CWj
     * 阿里密钥
     */
    private String  accessKeySecret="hUd5EmRbjp8HX6czYSSwkebyDsvs1z";
     //  ######阿里云文件上传oss##########
    /**
     * 地域节点
     */
    private String  aliOssEndpoint="sudakeji.oss-cn-hongkong.aliyuncs.com";
    /**
     * 文件名字
     */
    private String  aliOssBucketNameUser="sudakeji";

    private Integer codeLength =6;

    //=========================虚拟币========================================================
    /**
     * 以太坊请求节点地址
     */
    private String  ethNodesReqAddr="https://mainnet.infura.io/3f727c7ce8434106809d5cc2463ff779";

    /**
     * 以太坊钱包交易记录
     */
    private String ethQueryOrderRecordReqAddr = "http://api.etherscan.io/api?module=account&action=txlist&page=&offset=&sort=desc&apikey=7X8Y5FA3K5FSG1D3S57B6F5EBPT9QC1AYR";

    /**
     * 推广网页 和二维码地址
     */
    private String popularizePageUrl;
    /**
     * 推广网页 和二维码地址
     */
    private String popularizeQrCode;


    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
