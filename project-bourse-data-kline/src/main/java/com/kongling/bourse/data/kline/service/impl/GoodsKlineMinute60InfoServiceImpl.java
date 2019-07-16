package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinute60Info;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineMinute60InfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineDayInfoService;
import com.kongling.bourse.data.kline.service.IGoodsKlineMinute60InfoService;
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
 * 商品60分钟分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
@Slf4j
public class GoodsKlineMinute60InfoServiceImpl extends ServiceImpl<GoodsKlineMinute60InfoMapper, GoodsKlineMinute60Info> implements IGoodsKlineMinute60InfoService {


    private static Map<String, GoodsKlineMinute60Info> goodsKlineMinute60InfoHashMap = Maps.newHashMap();

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private IGoodsKlineDayInfoService goodsKlineDayInfoService;

    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineMinute60Info> list = baseMapper.selectList(new QueryWrapper<GoodsKlineMinute60Info>()
                .eq("code", vo.getStockCode())
                .between(
                        vo.getStartDate() != null && vo.getEndDate() != null,
                        "date", vo.getStartDate(), vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>) (Object) list;
    }

    @Override
    public void toCompoundData60Minute(GoodsOrghisInfo stockOrghisInfo) {
        //原始时间
        Date iniDate = stockOrghisInfo.getDateTime();
        String stockCode = stockOrghisInfo.getCode();

        /******* 60分钟数据*********************/
        Date date60Start = DateUtil.dateMinuteStart(iniDate, 60);
        BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
        GoodsKlineMinute60Info goods60 = goodsKlineMinute60InfoHashMap.get(stockCode);
        if (goods60 == null || goods60.getDate().before(date60Start)) {
            goods60 = baseMapper.selectOne(new QueryWrapper<GoodsKlineMinute60Info>()
                    .eq("code", stockCode)
                    .eq("date", date60Start));
        }
        if (goods60 != null && goods60.getDate().compareTo(date60Start) == 0) {

            GoodsKlineMinute60Info goods60s = goods60;
            taskExecutor.execute(() -> {
                if (newPrice.compareTo(goods60s.getHigh()) > 0) {
                    goods60s.setHigh(newPrice);
                }
                if (newPrice.compareTo(goods60s.getLow()) < 0) {
                    goods60s.setLow(newPrice);
                }
                goods60s.setClosePrice(newPrice);
                goodsKlineMinute60InfoHashMap.put(stockCode, goods60s);
                baseMapper.update(null, new UpdateWrapper<GoodsKlineMinute60Info>()
                        .setSql("volume=volume+" + stockOrghisInfo.getVolume())
                        .set("highest_price", goods60s.getHigh())
                        .set("lowest_price", goods60s.getLow())
                        .set("closing_price", goods60s.getClosePrice())
                        .eq("id", goods60s.getId()));
            });
        } else if (goods60 == null || goods60.getDate().before(date60Start)) {
            GoodsKlineMinute60Info minuteData60Synthesis = new GoodsKlineMinute60Info();
            minuteData60Synthesis.setLow(newPrice);
            minuteData60Synthesis.setHigh(newPrice);
            minuteData60Synthesis.setOpenPrice(newPrice);
            minuteData60Synthesis.setClosePrice(newPrice);
            minuteData60Synthesis.setCode(stockOrghisInfo.getCode());

            if (goods60 != null) {
                minuteData60Synthesis.setPrevClose(goods60.getClosePrice().stripTrailingZeros().toPlainString());
            }
            minuteData60Synthesis.setDate(date60Start);
            Date nowDate = DealDateUtil.getNowDate();
            minuteData60Synthesis.setTimestamp(nowDate);
            minuteData60Synthesis.setCreateTime(nowDate);
            minuteData60Synthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());

            baseMapper.insert(minuteData60Synthesis);
            goodsKlineMinute60InfoHashMap.put(stockCode, goods60);
        }


    }

    /**
     * 根据30分钟更新 60分钟数据
     *
     * @param goodss
     */
    @Override
    public void toCompoundData60MinuteByMinute30(GoodsKlineMinuteInfo goodss) {
        //原始时间
        Date iniDate = goodss.getDate();
        String stockCode = goodss.getCode();

        /******* 60分钟数据*********************/
        Date date60Start = DateUtil.dateMinuteStart(iniDate, 60);
        GoodsKlineMinute60Info goods60 = goodsKlineMinute60InfoHashMap.get(stockCode);
        if (goods60 == null || goods60.getDate().before(date60Start)) {
            goods60 = baseMapper.selectOne(new QueryWrapper<GoodsKlineMinute60Info>()
                    .eq("code", stockCode)
                    .eq("date", date60Start));
        }
        if (goods60 != null && goods60.getDate().compareTo(date60Start) == 0) {
            if (goodss.getHigh().compareTo(goods60.getHigh()) > 0) {
                goods60.setHigh(goodss.getHigh());
            }
            if (goodss.getLow().compareTo(goods60.getLow()) < 0) {
                goods60.setLow(goodss.getLow());
            }
            goods60.setClosePrice(goodss.getClosePrice());
            goods60.setVolume(goods60.getVolume().add(goodss.getVolume()));
            baseMapper.update(null, new UpdateWrapper<GoodsKlineMinute60Info>()
                    .setSql("volume=volume+" + goodss.getVolume())
                    .set("highest_price", goods60.getHigh())
                    .set("lowest_price", goods60.getLow())
                    .set("closing_price", goods60.getClosePrice())
                    .eq("id", goods60.getId()));
            taskExecutor.execute(() -> {
                try {
                    goodsKlineDayInfoService.toCompoundDataDayByMinute60(goodss);
                } catch (Exception e) {
                    log.info("天数据更新失败：" + ExceptionUtils.getFullStackTrace(e));
                }
            });
            goodsKlineMinute60InfoHashMap.put(stockCode, goods60);
        } else if (goods60 == null || goods60.getDate().before(date60Start)) {
            GoodsKlineMinute60Info minuteData60Synthesis = new GoodsKlineMinute60Info();
            minuteData60Synthesis.setLow(goodss.getLow());
            minuteData60Synthesis.setHigh(goodss.getHigh());
            minuteData60Synthesis.setOpenPrice(goodss.getOpenPrice());
            minuteData60Synthesis.setClosePrice(goodss.getClosePrice());
            minuteData60Synthesis.setCode(stockCode);
            minuteData60Synthesis.setVolume(goodss.getVolume());
            if(goods60 ==null) {
                goods60 = goodsKlineMinute60InfoHashMap.get(stockCode);
            }
            if (goods60 != null) {
                minuteData60Synthesis.setPrevClose(goods60.getClosePrice().stripTrailingZeros().toPlainString());
            }
            minuteData60Synthesis.setDate(date60Start);
            Date nowDate = DealDateUtil.getNowDate();
            minuteData60Synthesis.setTimestamp(nowDate);
            minuteData60Synthesis.setCreateTime(nowDate);
            minuteData60Synthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());

            baseMapper.insert(minuteData60Synthesis);
            goodsKlineMinute60InfoHashMap.put(stockCode, minuteData60Synthesis);
        }
    }
}
