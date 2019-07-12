package com.util.sms.lawcoin;

/**
 * @author 国威
 * @package com.sskj.common.constant
 * @date 2018-12-08  13:52
 * @project otc
 */
public class MessageConstant {

    /*购买订单10分钟未付款 */
    public static final String BUY_10_NOPAY="SMS_151905021";

    /*购买订单15分钟超时取消*/
    public static final String BUY_15_OUT_TIME="SMS_151835367";

    /*购买订单完成*/
    public static final String BUY_SUCESS="SMS_151900170";

    /*出售订单生成订单*/
    public static final String TRANSFER_REMIND="SMS_151900166";

    /*出售订单买家已付款*/
    public static final String SELL_TRANSFER_SUCCESS="SMS_151835391";

    /*出售订单完成*/
    public static final String SELL_SUCCESS="SMS_151900148";

    /*购买订单完成   SMS_151900055*/
    public static final String BUY_ORDER_SUCCESS="SMS_151900055";

    /*提币验证码*/
    public static final String WIDTHDRAW_CODE="SMS_151905001";

    /*修改密码短信验证码*/
    public static final String ALTER_PASSWORD="SMS_151900026";


    /*尊敬的用户：您的订单${orderNo}还未付款，如已付款请进行确认，如未及时确认付款订单将自动取消。*/
    public static final String BUY_10_NOPAY_MESSAGE="尊敬的用户：您的订单%s还未付款，如已付款请进行确认，如未及时确认付款订单将自动取消。";

    /*尊敬的用户：您的订单${orderNo}未在15分钟内完成付款确认，已自动取消。*/
    public static final String BUY_15_OUT_TIME_MESSAGE="尊敬的用户：您的订单%s未在15分钟内完成付款确认，已自动取消。";

    /*尊敬的用户：${userName}已确认收到您的订单${orderNo}付款并放行，系统会自动将您购买的${payAmount}个${coinSimple}发放到您的云钱包，请注意查收。*/
    public static final String BUY_SUCESS_MESSAGE="尊敬的用户：%s已确认收到您的订单%s付款并放行，系统会自动将您购买的%s个%s发放到您的云钱包，请注意查收。";

    /*尊敬的用户：${userName}已向您的订单${orderNo}支付${payAmount}个${coinSimple}，请及时登录向付款账户支付${buyAmount}CNY。*/
    public static final String TRANSFER_REMIND_MESSAGE="尊敬的用户：%s已向您的订单%s支付%s个%s，请及时登录向付款账户支付%sCNY。";

    /*尊敬的用户：${userName}将订单${orderNo}标记为“已付款”状态，请及时登录收款账户查看，如收到付款请及时放行。*/
    public static final String SELL_TRANSFER_SUCCESS_MESSAGE="尊敬的用户：%s将订单%s标记为“已付款”状态，请及时登录收款账户查看，如收到付款请及时放行。";

    /*尊敬的用户：${userName}已确认收到您的订单${orderNo}付款并放行，系统会自动将您购买的${payAmount}个${coinSimple}发放到您的云钱包，请注意查收。*/
    public static final String SELL_SUCCESS_MESSAGE="尊敬的用户：%s已确认收到您的订单%s付款并放行，系统会自动将您购买的%s个%s发放到您的云钱包，请注意查收。";

    /*尊敬的用户：您已向${userName}用户购买了${payAmount}个${coinSimple},购买单价为${purchasePrice}CNY/${specieSimple},订单为${orderNo}。*/
    public static final String BUY_ORDER_SUCCESS_MESSAGE="尊敬的用户：您已向%s用户购买了%s个%s,购买单价为%sCNY/%s,订单为%s。";

    /*尊敬的用户：您的验证码为${verificateNumber}，您正在进行提币操作，若非本人操作，请及时修改密码。*/
    public static final String WIDTHDRAW_CODE_MESSAGE="尊敬的用户：您的验证码为%s，您正在进行提币操作，若非本人操作，请及时修改密码。";

    /*尊敬的用户：您的验证码为${verificateNumber}，您正在进行修改支付密码操作，若非本人操作，请及时修改密码。*/
    public static final String ALTER_PASSWORD_MESSAGE="尊敬的用户：您的验证码为%s，您正在进行修改支付密码操作，若非本人操作，请及时修改密码。";


    public static final String orderNo="orderNo";

    public static final String userName="userName";

    public static final String payAmount="payAmount";

    public static final String coinSimple="coinSimple";

    public static final String buyAmount="buyAmount";

    public static final String purchasePrice="purchasePrice";

    public static final String specieSimple="specieSimple";

    public static final String verificateNumber="verificateNumber";



}
