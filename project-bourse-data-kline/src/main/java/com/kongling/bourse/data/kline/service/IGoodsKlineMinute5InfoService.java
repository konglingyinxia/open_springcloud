package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute5Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品5分钟分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineMinute5InfoService extends IService<GoodsKlineMinute5Info> {

    List<Object> listHistoryByVo(KlineQueryVo vo);

    @Deprecated
    void toCompoundData5Minute(GoodsOrghisInfo stockOrghisInfo);

    /**
     * 新增通过一分钟数据更新五数据
     *
     * @param goodss
     */
    void toCompoundData5MinuteByMinute(GoodsKlineMinuteInfo goodss);
}
