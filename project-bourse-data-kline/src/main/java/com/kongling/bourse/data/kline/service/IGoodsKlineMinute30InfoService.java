package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute30Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品30分钟分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineMinute30InfoService extends IService<GoodsKlineMinute30Info> {

    List<Object> listHistoryByVo(KlineQueryVo vo);
    @Deprecated
    void toCompoundData30Minute(GoodsOrghisInfo stockOrghisInfo);

    /**
     * 根据十五分钟合成30分钟数据
     *
     * @param goodss
     */
    void toCompoundData30MinuteByMinute15(GoodsKlineMinuteInfo goodss);
}
