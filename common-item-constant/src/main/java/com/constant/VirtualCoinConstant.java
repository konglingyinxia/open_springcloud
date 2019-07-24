package com.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 虚拟币系列
 *
 * @author kongling
 * @package com.constant
 * @date 2019-05-24  11:46
 * @project suda_cloud
 */
public class VirtualCoinConstant {

   public static List<String> ETH_VIRTUAL_COIN_SERIES= Lists.newArrayList(
           "ETH"
   );
    public static List<String> BTC_VIRTUAL_COIN_SERIES= Lists.newArrayList(
            "BTC","USDT"
    );
    public static List<String> PLAT_VIRTUAL_COIN_SERIES= Lists.newArrayList(
            "SDT"
    );


    /**
     * ETH交易单位为 wei
     * 1 ether = 1000000000000000000 wei
     */
    public static final Long aLong = 1000000000000000000L;

    /**
     * btc usdt  区块确认最低次数
     */
    public static final  int BTC_CONFIRMATIONS_NUM = 3;



}
