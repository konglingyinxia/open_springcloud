package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute15Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品15分钟分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineMinute15InfoService extends IService<GoodsKlineMinute15Info> {

    List<Object> listHistoryByVo(KlineQueryVo vo);

    @Deprecated
    void toCompoundData15Minute(GoodsOrghisInfo stockOrghisInfo);

    /**
     * 根据十五分钟数据更新数据
     *
     * @param goodss
     */
    void toCompoundData15MinuteByMinute5(GoodsKlineMinuteInfo goodss);
}
