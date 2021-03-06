package com.kongling.bourse.data.kline.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;

import java.util.List;

/**
 * <p>
 * 商品分时信息 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsKlineMinuteInfoService extends IService<GoodsKlineMinuteInfo> {

    List<Object> listHistoryByVo(KlineQueryVo vo);

    void toCompoundDataMinute(GoodsOrghisInfo stockOrghisInfo);
}
