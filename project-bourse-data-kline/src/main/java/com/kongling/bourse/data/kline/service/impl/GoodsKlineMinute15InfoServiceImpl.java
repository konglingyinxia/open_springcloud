package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute15Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineMinute15InfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineMinute15InfoService;
import com.util.DateUtil;
import com.util.DealDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品15分钟分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
public class GoodsKlineMinute15InfoServiceImpl extends ServiceImpl<GoodsKlineMinute15InfoMapper, GoodsKlineMinute15Info> implements IGoodsKlineMinute15InfoService {

    private static Map<String, GoodsKlineMinute15Info> goodsKlineMinute15InfoHashMap = Maps.newHashMap();

    @Autowired
    TaskExecutor taskExecutor;

    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineMinute15Info> list = baseMapper.selectList(new QueryWrapper<GoodsKlineMinute15Info>()
                .eq("code",vo.getStockCode())
                .between(
                        vo.getStartDate()!=null && vo.getEndDate()!=null,
                        "date",vo.getStartDate(),vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>)(Object)list;
    }

    @Override
    public void toCompoundData15Minute(GoodsOrghisInfo stockOrghisInfo) {
        //原始时间
        Date iniDate = stockOrghisInfo.getDateTime();
        String stockCode =stockOrghisInfo.getCode();

        /******* 十五分钟数据*********************/
        Date tFiveDateStart = DateUtil.dateMinuteStart(iniDate, 15);
        BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
        GoodsKlineMinute15Info goods15= goodsKlineMinute15InfoHashMap.get(stockCode);
        if(goods15==null || goods15.getDate().before(tFiveDateStart)){
            goods15 =  baseMapper.selectOne(new QueryWrapper<GoodsKlineMinute15Info>()
                    .eq("code",stockCode)
                    .eq("date",tFiveDateStart));
        }
        if (goods15 != null && goods15.getDate().compareTo(tFiveDateStart)==0) {
                final  GoodsKlineMinute15Info goods15s=goods15;

                taskExecutor.execute(()->{
                    if (newPrice.compareTo(goods15s.getHigh()) > 0) {
                        goods15s.setHigh(newPrice);
                    }
                    if (newPrice.compareTo(goods15s.getLow()) < 0) {
                        goods15s.setLow(newPrice);
                    }
                    goods15s.setClosePrice(newPrice);
                    goodsKlineMinute15InfoHashMap.put(stockCode,goods15s);
                    baseMapper.update(null,new UpdateWrapper<GoodsKlineMinute15Info>()
                            .setSql("volume=volume+"+stockOrghisInfo.getVolume())
                            .set("highest_price",goods15s.getHigh())
                            .set("lowest_price",goods15s.getLow())
                            .set("closing_price",goods15s.getClosePrice())
                            .eq("id",goods15s.getId()));
                });
        } else if(goods15==null || goods15.getDate().before(tFiveDateStart)) {
            GoodsKlineMinute15Info tFiveMinuteDataSynthesis = new GoodsKlineMinute15Info();
            tFiveMinuteDataSynthesis.setLow(newPrice);
            tFiveMinuteDataSynthesis.setHigh(newPrice);
            tFiveMinuteDataSynthesis.setOpenPrice(newPrice);
            tFiveMinuteDataSynthesis.setClosePrice(newPrice);
            tFiveMinuteDataSynthesis.setCode(stockOrghisInfo.getCode());

            goods15 =goodsKlineMinute15InfoHashMap.get(stockCode);
            if(goods15!=null){
                tFiveMinuteDataSynthesis.setPrevClose(goods15.getPrice());
            }

            tFiveMinuteDataSynthesis.setDate(tFiveDateStart);
            Date nowDate = DealDateUtil.getNowDate();
            tFiveMinuteDataSynthesis.setTimestamp(nowDate);
            tFiveMinuteDataSynthesis.setCreateTime(nowDate);
            tFiveMinuteDataSynthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());

            baseMapper.insert(tFiveMinuteDataSynthesis);

            goodsKlineMinute15InfoHashMap.put(stockCode,goods15);
        }

    }
}
