package com.kongling.platform.entity.VO.homeCount;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author kongling
 * @package com.suda.platform.VO.homeCount
 * @date 2019-06-20  16:02
 * @project suda_cloud
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class HomeCountVO {
    /**
     * 注册用户总数
     */
    public Long stockUserNum;

    /**
     * 今日注册人数
     */
    public Long stockUserDayNum;

    /**
     * 实名认证用户数量
     */
    public Long stockUserAuthNum;

    /**
     * 未认证用户
     */
    public Long stockUserNoAuthNum;
    /**
     * 待认证用户
     */
    public Long stockUserWaitAuthNum;
    /**
     * 平台法币总成交单
     */
    public Long lawCoinAllDealOrder;
    /**
     * 平台法币总交易单
     */
    public Long lawCoinAllOrder;

    /**
     * 平台法币日成交单
     */
    public Long lawCoinDayDealOrder;
    /**
     * 平台法币日交易单
     */
    public Long lawCoinDayOrder;

    /**
     * 平台币种数量
     */
    public List<Map<String,Object>> coinCodeNum;



}
