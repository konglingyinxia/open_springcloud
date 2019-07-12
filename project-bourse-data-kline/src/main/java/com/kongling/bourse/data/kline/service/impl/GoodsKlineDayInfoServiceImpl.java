package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineDayInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineDayInfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineDayInfoService;
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
 * 商品天分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
public class GoodsKlineDayInfoServiceImpl extends ServiceImpl<GoodsKlineDayInfoMapper, GoodsKlineDayInfo> implements IGoodsKlineDayInfoService {

    private static Map<String, GoodsKlineDayInfo> goodsKlineDayInfoHashMap = Maps.newHashMap();

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineDayInfo> list = baseMapper.selectList(new QueryWrapper<GoodsKlineDayInfo>()
                .eq("code",vo.getStockCode())
                .between(
                        vo.getStartDate()!=null && vo.getEndDate()!=null,
                        "date",vo.getStartDate(),vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>)(Object)list;
    }



    @Override
    public void toCompoundDataDay(GoodsOrghisInfo stockOrghisInfo) {
        //原始时间
        Date iniDate = stockOrghisInfo.getDateTime();
        String stockCode =stockOrghisInfo.getCode();
        /**
         * 日线数据
         */
        Date dateDayStart = DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate();
        BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
        GoodsKlineDayInfo goodsDay= goodsKlineDayInfoHashMap.get(stockCode);
        if(goodsDay==null || goodsDay.getDate().before(dateDayStart)){
            goodsDay =  baseMapper.selectOne(new QueryWrapper<GoodsKlineDayInfo>()
                    .eq("code",stockCode)
                    .eq("date",dateDayStart));
        }
        if (goodsDay != null && goodsDay.getDate().compareTo(dateDayStart)==0) {
            if(newPrice.compareTo(goodsDay.getClosePrice())!=0){
              final   GoodsKlineDayInfo goodsDays =goodsDay;
                taskExecutor.execute(()->{
                    if (newPrice.compareTo(goodsDays.getHigh()) > 0) {
                        goodsDays.setHigh(newPrice);
                    }
                    if (newPrice.compareTo(goodsDays.getLow()) < 0) {
                        goodsDays.setLow(newPrice);
                    }
                    goodsDays.setClosePrice(newPrice);
                    goodsKlineDayInfoHashMap.put(stockCode,goodsDays);
                    baseMapper.update(null,new UpdateWrapper<GoodsKlineDayInfo>()
                            .setSql("volume=volume+"+stockOrghisInfo.getVolume())
                            .set("highest_price",goodsDays.getHigh())
                            .set("lowest_price",goodsDays.getLow())
                            .set("closing_price",goodsDays.getClosePrice())
                            .eq("id",goodsDays.getId()));
                });
            }
        } else   if(goodsDay==null || goodsDay.getDate().before(dateDayStart)){
            GoodsKlineDayInfo minuteDataDaySynthesis = new GoodsKlineDayInfo();
            minuteDataDaySynthesis.setLow(newPrice);
            minuteDataDaySynthesis.setHigh(newPrice);
            minuteDataDaySynthesis.setOpenPrice(newPrice);
            minuteDataDaySynthesis.setClosePrice(newPrice);
            minuteDataDaySynthesis.setCode(stockOrghisInfo.getCode());

            goodsDay =goodsKlineDayInfoHashMap.get(stockCode);
            if(goodsDay!=null){
                minuteDataDaySynthesis.setPrevClose(goodsDay.getPrice());
            }
            minuteDataDaySynthesis.setDate(dateDayStart);
            Date nowDate = DealDateUtil.getNowDate();
            minuteDataDaySynthesis.setTimestamp(nowDate);
            minuteDataDaySynthesis.setCreateTime(nowDate);
            minuteDataDaySynthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());
            baseMapper.insert(minuteDataDaySynthesis);

            goodsKlineDayInfoHashMap.put(stockCode,goodsDay);
        }

    }
}
