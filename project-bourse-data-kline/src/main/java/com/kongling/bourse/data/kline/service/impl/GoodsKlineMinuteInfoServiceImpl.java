package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.entity.PO.GoodsKlineMinuteInfo;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.mapper.GoodsKlineMinuteInfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsKlineMinuteInfoService;
import com.util.DateUtil;
import com.util.DealDateUtil;
import config.redis.RedisUtils;
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
 * 商品分时信息 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
@Slf4j
public class GoodsKlineMinuteInfoServiceImpl extends ServiceImpl<GoodsKlineMinuteInfoMapper, GoodsKlineMinuteInfo> implements IGoodsKlineMinuteInfoService {

    private static Map<String,GoodsKlineMinuteInfo>  goodsKlineMinuteInfoMap = Maps.newHashMap();

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public List<Object> listHistoryByVo(KlineQueryVo vo) {
        List<GoodsKlineMinuteInfo> list = baseMapper.selectList(new QueryWrapper<GoodsKlineMinuteInfo>()
                .eq("code",vo.getStockCode())
                .between(
                        vo.getStartDate()!=null && vo.getEndDate()!=null,
                        "date",vo.getStartDate(),vo.getEndDate())
                .orderByDesc("date"));
        return (List<Object>)(Object)list;
    }

    @Override
    public void toCompoundDataMinute(GoodsOrghisInfo stockOrghisInfo) {
        String stockCode = stockOrghisInfo.getCode();
        try {
            //原始时间
            Date iniDate = stockOrghisInfo.getDateTime();

            /******* 一分钟数据*********************/
            Date oneDateStart = DateUtil.dateMinuteStart(iniDate, 1);
            BigDecimal newPrice = new BigDecimal(stockOrghisInfo.getOpen());
            GoodsKlineMinuteInfo goods= goodsKlineMinuteInfoMap.get(stockCode);
            if(goods==null|| goods.getDate().before(oneDateStart)){
                goods =  baseMapper.selectOne(new QueryWrapper<GoodsKlineMinuteInfo>()
                        .eq("code",stockCode)
                        .eq("date",oneDateStart));
            }
            if (goods != null && goods.getDate().compareTo(oneDateStart)==0) {
                /*if(newPrice.compareTo(goods.getClosePrice())!=0){*/
                final GoodsKlineMinuteInfo  goodss =goods;
                taskExecutor.execute(()->{
                    if (newPrice.compareTo(goodss.getHigh()) > 0) {
                        goodss.setHigh(newPrice);
                    }
                    if (newPrice.compareTo(goodss.getLow()) < 0) {
                        goodss.setLow(newPrice);
                    }
                    goodss.setVolume(goodss.getVolume().add(new BigDecimal(stockOrghisInfo.getVolume())));
                    goodss.setClosePrice(newPrice);
                    goodsKlineMinuteInfoMap.put(stockCode,goodss);
                    baseMapper.update(null,new UpdateWrapper<GoodsKlineMinuteInfo>()
                            .setSql("volume=volume+"+stockOrghisInfo.getVolume())
                            .set("highest_price",goodss.getHigh())
                            .set("lowest_price",goodss.getLow())
                            .set("closing_price",goodss.getClosePrice())
                            .eq("id",goodss.getId()));
                });

               /* }*/
            } else if(goods ==null|| goods.getDate().before(oneDateStart)){
                GoodsKlineMinuteInfo oneMinuteDataSynthesis = new GoodsKlineMinuteInfo();
                oneMinuteDataSynthesis.setLow(newPrice);
                oneMinuteDataSynthesis.setHigh(newPrice);
                oneMinuteDataSynthesis.setOpenPrice(newPrice);
                oneMinuteDataSynthesis.setClosePrice(newPrice);
                oneMinuteDataSynthesis.setCode(stockCode);
                oneMinuteDataSynthesis.setVolume(new BigDecimal(stockOrghisInfo.getVolume()));
                goods =goodsKlineMinuteInfoMap.get(stockCode);
                if(goods!=null){
                    oneMinuteDataSynthesis.setPrevClose(goods.getPrice());
                }
                oneMinuteDataSynthesis.setDate(oneDateStart);
                Date nowDate = DealDateUtil.getNowDate();
                oneMinuteDataSynthesis.setTimestamp(nowDate);
                oneMinuteDataSynthesis.setCreateTime(nowDate);
                oneMinuteDataSynthesis.setDateYmd(DealDateUtil.getYearMonthDay(DealDateUtil.dateToLocalDateTime(iniDate)).toDate());
                baseMapper.insert(oneMinuteDataSynthesis);
                goodsKlineMinuteInfoMap.put(stockCode,oneMinuteDataSynthesis);
            }
        }catch (Exception e){
            log.error("合成一分钟数据失败：\n"+ ExceptionUtils.getFullStackTrace(e));
        }
    }
}
