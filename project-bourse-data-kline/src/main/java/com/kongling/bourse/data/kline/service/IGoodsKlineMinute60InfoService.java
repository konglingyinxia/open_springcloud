package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute60Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品60分钟分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineMinute60InfoService extends IService<GoodsKlineMinute60Info> {

    List<Object> listHistoryByVo(KlineQueryVo vo);

    @Deprecated
    void toCompoundData60Minute(GoodsOrghisInfo stockOrghisInfo);

    /**
     * 根据30分钟更新 60分钟数据
     *
     * @param goodss
     */
    void toCompoundData60MinuteByMinute30(GoodsKlineMinuteInfo goodss);
}
