package com.kongling.bourse.data.kline.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.service.*;
import com.util.pageinfoutil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kongling
 * @package com.kongling.bourse.data.kline.service.impl
 * @date 2019-05-28  10:36
 * @project suda_cloud
 */
@Service
public class KlineServiceImpl implements IKlineService {
    @Autowired
    private IGoodsKlineMinuteInfoService goodsKlineMinuteInfoService;
    @Autowired
    private IGoodsKlineMinute5InfoService goodsKlineMinute5InfoService;
    @Autowired
    private IGoodsKlineMinute15InfoService goodsKlineMinute15InfoService;
    @Autowired
    private IGoodsKlineMinute30InfoService goodsKlineMinute30InfoService;
    @Autowired
    private IGoodsKlineMinute60InfoService goodsKlineMinute60InfoService;
    @Autowired
    private IGoodsKlineDayInfoService goodsKlineDayInfoService;





    @Override
    public PageInfo<Object> listHistoryByVo(KlineQueryVo vo, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<Object> list = Lists.newArrayList();
        switch (vo.getTimeCycle()){
            case "minute":
                list = goodsKlineMinuteInfoService.listHistoryByVo(vo);
                break;
            case "minute5":
                list = goodsKlineMinute5InfoService.listHistoryByVo(vo);
                break;
            case "minute15":
                list = goodsKlineMinute15InfoService.listHistoryByVo(vo);
                break;
            case "minute30":
                list = goodsKlineMinute30InfoService.listHistoryByVo(vo);
                break;
            case "minute60":
                list = goodsKlineMinute60InfoService.listHistoryByVo(vo);
                break;
            case "day":
                list = goodsKlineDayInfoService.listHistoryByVo(vo);
                break;
            default:
                break;
        }
        return new PageInfo<>(list);
    }
}
