package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute30Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineMinute30InfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineMinute30InfoService;
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
 * 商品30分钟分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
public class GoodsKlineMinute30InfoServiceImpl extends ServiceImpl<GoodsKlineMinute30InfoMapper, GoodsKlineMinute30Info> implements IGoodsKlineMinute30InfoService {


    private static Map<String, GoodsKlineMinute30Info> goodsKlineMinute30InfoHashMap = Maps.newHashMap();

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineMinute30Info> list = baseMapper.selectList(new QueryWrapper<GoodsKlineMinute30Info>()
                .eq("code",vo.getStockCode())
                .between(
                        vo.getStartDate()!=null && vo.getEndDate()!=null,
                        "date",vo.getStartDate(),vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>)(Object)list;
    }

    @Override
    public void toCompoundData30Minute(GoodsOrghisInfo stockOrghisInfo) {
        //原始时间
        Date iniDate = stockOrghisInfo.getDateTime();
        String stockCode =stockOrghisInfo.getCode();
        /******* 30分钟数据*********************/
        Date thirtyDateStart = DateUtil.dateMinuteStart(iniDate, 30);
        BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
        GoodsKlineMinute30Info goods30= goodsKlineMinute30InfoHashMap.get(stockCode);
        if(goods30==null ||goods30.getDate().before(thirtyDateStart)){
            goods30 =  baseMapper.selectOne(new QueryWrapper<GoodsKlineMinute30Info>()
                    .eq("code",stockCode)
                    .eq("date",thirtyDateStart));
        }
        if (goods30 != null && goods30.getDate().compareTo(thirtyDateStart) ==0) {
                final  GoodsKlineMinute30Info goods30s=goods30;
                taskExecutor.execute(()->{
                    if (newPrice.compareTo(goods30s.getHigh()) > 0) {
                        goods30s.setHigh(newPrice);
                    }
                    if (newPrice.compareTo(goods30s.getLow()) < 0) {
                        goods30s.setLow(newPrice);
                    }
                    goods30s.setClosePrice(newPrice);
                    goodsKlineMinute30InfoHashMap.put(stockCode,goods30s);
                    baseMapper.update(null,new UpdateWrapper<GoodsKlineMinute30Info>()
                            .setSql("volume=volume+"+stockOrghisInfo.getVolume())
                            .set("highest_price",goods30s.getHigh())
                            .set("lowest_price",goods30s.getLow())
                            .set("closing_price",goods30s.getClosePrice())
                            .eq("id",goods30s.getId()));
                });
        } else  if(goods30==null || goods30.getDate().before(thirtyDateStart)){
            GoodsKlineMinute30Info thirtyMinuteDataSynthesis = new GoodsKlineMinute30Info();
            thirtyMinuteDataSynthesis.setLow(newPrice);
            thirtyMinuteDataSynthesis.setHigh(newPrice);
            thirtyMinuteDataSynthesis.setOpenPrice(newPrice);
            thirtyMinuteDataSynthesis.setClosePrice(newPrice);
            thirtyMinuteDataSynthesis.setCode(stockOrghisInfo.getCode());

            goods30 = goodsKlineMinute30InfoHashMap.get(stockCode);
            if(goods30!=null){
                thirtyMinuteDataSynthesis.setPrevClose(goods30.getPrice());
            }

            thirtyMinuteDataSynthesis.setDate(thirtyDateStart);
            Date nowDate = DealDateUtil.getNowDate();
            thirtyMinuteDataSynthesis.setTimestamp(nowDate);
            thirtyMinuteDataSynthesis.setCreateTime(nowDate);
            thirtyMinuteDataSynthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());

            baseMapper.insert(thirtyMinuteDataSynthesis);
            goodsKlineMinute30InfoHashMap.put(stockCode,goods30);
        }

    }
}
