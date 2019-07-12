package com.util.sms.lawcoin;

import com.util.sms.networdbuild.NetWordBuildParam;
import com.util.sms.networdbuild.NetWordBuildSendUtil;

import java.io.IOException;

/**
 * @author 卫星
 * @package com.sskj.common.sendmsg
 * @date 2018-12-12  15:03
 * @project OTC
 */

public class SendMsgUtils {
    /**
     * 发送注册验证码
     */
    public static String sendCode(NetWordBuildParam sms) throws IOException {
        return   NetWordBuildSendUtil.send(sms.getMobile(),"您好，您本次的验证码是" + sms.getCode() + ",5分钟内有效，谨防泄露！");

    }



    /**
     * 买家标记为付款
     */
    public static String sendSellTransferSuccess(String username, String mobile, String orderNo) throws IOException {
        String text= "尊敬的"+username+"先生，您好，你的付款参考号为：" + orderNo + "的订单买家已确认付款，请及时查看！";
        return NetWordBuildSendUtil.send(mobile,text);


    }

    /**
     * 手动确认
     * 收到款放行
     * 购买订单完成
     * SMS_151900170
     *
     * @param username
     * @param orderNo
     * @param payAmount  个数
     * @param coinSimple 币
     * @param mobile
     */
    public static void sendHandOrderOverSucessMessage(String username, String orderNo, String payAmount, String coinSimple, String mobile) throws IOException {
        String text = "尊敬的用户："+username+"已确认收到您的订单"+orderNo+"付款并放行，系统会自动将您购买的"+payAmount
                +"个"+coinSimple+"发放到您的钱包，请注意查收。";
        NetWordBuildSendUtil.send(mobile,text);


    }
    /**
     * 自动确认
     * 收到款放行
     * 购买订单完成
     * SMS_151900170
     *
     * @param username
     * @param orderNo
     * @param payAmount  个数
     * @param coinSimple 币
     * @param mobile
     */
    public static void sendAutoOrderOverSucessMessage(String username, String orderNo, String payAmount, String coinSimple, String mobile) throws IOException {
        String text = "尊敬的用户："+username+"向你购买的"+payAmount+"个"+coinSimple+
                "，订单号为："+orderNo+",在指定时间内未确认放行，系统已自动放行";
        NetWordBuildSendUtil.send(mobile,text);


    }



    /**
     * 10分钟提示
     * SMS_151905021
     */
    public static void sendBuy10NoPayMessage(String userName, String orderNo, String mobile) throws IOException {

    }

    /**
     * 购买订单超时取消
     * SMS_151835367
     */
    public static void sendBuy15OutTimeMessage(String userName, String orderNo, String mobile) throws IOException {

    }

    /**
     * 出售订单生成订单
     * SMS_151900166
     *
     * @param userName
     * @param orderNo
     * @param payAmount
     * @param coinSimple
     * @param mobile
     * @throws IOException
     */

    public static void sendTransferRemindMessage(String userName, String orderNo, String payAmount, String coinSimple, String buyAmount, String mobile) throws IOException {

    }

    /**
     * 购买生成订单
     * SMS_151900055
     *
     * @param userName
     * @param orderNo
     * @param payAmount
     * @param coinSimple
     * @param purchasePrice
     * @param mobile
     * @throws IOException
     */

    public static void sendBuyOrderSuccessMessage(String userName, String orderNo, String payAmount, String coinSimple, String purchasePrice, String mobile) throws IOException {
            String text = "尊敬的用户：您已向%s用户购买了%s个%s,购买单价为%sCNY/%s,订单为%s。";

    }

    public static void main(String[] args) throws IOException {
        sendBuyOrderSuccessMessage("00", "00","00", "0", "0", "13298359981");
    }


}
