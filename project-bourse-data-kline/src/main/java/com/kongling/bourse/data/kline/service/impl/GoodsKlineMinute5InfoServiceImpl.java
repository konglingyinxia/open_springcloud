package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute5Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineMinute5InfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineMinute5InfoService;
import com.util.DateUtil;
import com.util.DealDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品5分钟分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
@Slf4j
public class GoodsKlineMinute5InfoServiceImpl extends ServiceImpl<GoodsKlineMinute5InfoMapper, GoodsKlineMinute5Info> implements IGoodsKlineMinute5InfoService {

    private static Map<String, GoodsKlineMinute5Info> goodsKlineMinute5InfoHashMap = Maps.newHashMap();

    @Autowired
    TaskExecutor taskExecutor;

    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineMinute5Info> list = baseMapper.selectList(new QueryWrapper<GoodsKlineMinute5Info>()
                .eq("code",vo.getStockCode())
                .between(
                        vo.getStartDate()!=null && vo.getEndDate()!=null,
                        "date",vo.getStartDate(),vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>)(Object)list;
    }

    @Override
    public void toCompoundData5Minute(GoodsOrghisInfo stockOrghisInfo) {
        String stockCode =stockOrghisInfo.getCode();
        try {
        //原始时间
        Date iniDate = stockOrghisInfo.getDateTime();
        /******* 五分钟数据*********************/
        Date fiveDateStart = DateUtil.dateMinuteStart(iniDate, 5);
        BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
        GoodsKlineMinute5Info goods5= goodsKlineMinute5InfoHashMap.get(stockCode);
        if(goods5==null || goods5.getDate().before(fiveDateStart)){
            goods5 =  baseMapper.selectOne(new QueryWrapper<GoodsKlineMinute5Info>()
                    .eq("code",stockCode)
                    .eq("date",fiveDateStart));
        }
        if (goods5 != null && goods5.getDate().compareTo(fiveDateStart)==0) {
                final  GoodsKlineMinute5Info goods5s=goods5;
                taskExecutor.execute(()->{
                    if (newPrice.compareTo(goods5s.getHigh()) > 0) {
                        goods5s.setHigh(newPrice);
                    }
                    if (newPrice.compareTo(goods5s.getLow()) < 0) {
                        goods5s.setLow(newPrice);
                    }
                    goods5s.setClosePrice(newPrice);
                    goodsKlineMinute5InfoHashMap.put(stockCode,goods5s);
                    baseMapper.update(null,new UpdateWrapper<GoodsKlineMinute5Info>()
                            .setSql("volume=volume+"+stockOrghisInfo.getVolume())
                            .set("highest_price",goods5s.getHigh())
                            .set("lowest_price",goods5s.getLow())
                            .set("closing_price",goods5s.getClosePrice())
                            .eq("id",goods5s.getId()));
                });
        } else if(goods5==null || goods5.getDate().before(fiveDateStart)){
            GoodsKlineMinute5Info fiveMinuteDataSynthesis = new GoodsKlineMinute5Info();
            fiveMinuteDataSynthesis.setLow(newPrice);
            fiveMinuteDataSynthesis.setHigh(newPrice);
            fiveMinuteDataSynthesis.setOpenPrice(newPrice);
            fiveMinuteDataSynthesis.setClosePrice(newPrice);
            fiveMinuteDataSynthesis.setCode(stockOrghisInfo.getCode());

            goods5 =goodsKlineMinute5InfoHashMap.get(stockCode);
            if(goods5!=null){
                fiveMinuteDataSynthesis.setPrevClose(goods5.getPrice());
            }

            fiveMinuteDataSynthesis.setDate(fiveDateStart);
            Date nowDate = DealDateUtil.getNowDate();
            fiveMinuteDataSynthesis.setTimestamp(nowDate);
            fiveMinuteDataSynthesis.setCreateTime(nowDate);
            fiveMinuteDataSynthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());
            baseMapper.insert(fiveMinuteDataSynthesis);
            goodsKlineMinute5InfoHashMap.put(stockCode,goods5);
        }
        }catch (Exception e){
            log.error("合成5分钟数据失败：\n"+ ExceptionUtils.getFullStackTrace(e));
        }
   }
}
