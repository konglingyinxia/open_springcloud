package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineDayInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品天分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineDayInfoService extends IService<GoodsKlineDayInfo> {

    List<Object> listHistoryByVo(KlineQueryVo vo);

    @Deprecated
    void toCompoundDataDay(GoodsOrghisInfo stockOrghisInfo);

    /**
     * 根据六十分钟数据合成天数据
     *
     * @param goodss
     */
    void toCompoundDataDayByMinute60(GoodsKlineMinuteInfo goodss);
}
