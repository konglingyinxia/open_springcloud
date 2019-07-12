package com.kongling.bourse.data.kline.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.constant.CommonConstant;
import com.constant.RedisKeysPrefix;
import com.kongling.bourse.data.kline.common.service.ICommonDataService;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.StockCodeRealTimeVO;
import com.kongling.bourse.data.kline.entity.enums.KLineTimeCycleEnum;
import com.kongling.bourse.data.kline.service.*;
import com.util.DealDateUtil;
import config.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author kongling
 * @package com.kongling.bourse.data.kline.common.service.impl
 * @date 2019-05-29  14:51
 * @project suda_cloud
 */
@Component
@EnableScheduling
@Slf4j
public class CommonDataServiceImpl implements ICommonDataService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private StringRedisTemplate redisTemplate;

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

    @PostConstruct
    public void   listenRedisLawCoinDeal(){
        taskExecutor.execute(()->{
            while (true){
                String str =  redisUtils.rpopQueue(RedisKeysPrefix.FROM_LINKED_QUEUE_NEW_DATE_MESSAGE);
                if(str == null){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        JSONObject json = JSONObject.parseObject(str);
                        GoodsOrghisInfo orghisInfo = JSONObject.toJavaObject(json,GoodsOrghisInfo.class);
                        putGoodsOrghisInfo(orghisInfo);
                    }catch (Exception e){
                        log.info("合成数据调用方法错误：\n"+ ExceptionUtils.getFullStackTrace(e));
                    }
                }
            }
        });
    }

    /**
     * 存入实时成交数据
     *
     * @param orghisInfo
     */
    @Override
    public void putGoodsOrghisInfo(GoodsOrghisInfo orghisInfo) {
        //构造实时数据
        toCompoundDataRedis(orghisInfo);
        //构造分时k线数据
        for (KLineTimeCycleEnum dataCycleEnum : KLineTimeCycleEnum.values()) {
            commonCreateData(orghisInfo, dataCycleEnum.value);
        }
    }

    /**
     * 构造实时数据
     */
    private void toCompoundDataRedis(GoodsOrghisInfo orghisInfo ){
        taskExecutor.execute(()->{
            try{
                String newPrice = orghisInfo.getOpen();
                String stockCode = orghisInfo.getCode();
                BigDecimal volume = new BigDecimal(orghisInfo.getVolume());
                Date dateNow = orghisInfo.getDateTime();
                String dateStr = DealDateUtil.dateToDateString(dateNow);
                String timeStr = String.valueOf(dateNow.getTime());
                Date dataDate1 = DealDateUtil.stringDateToDate(dateStr);
                String cnyPrice = (new BigDecimal(newPrice)).setScale(2, RoundingMode.DOWN).toPlainString();
                String stockProductVOStr = redisUtils.getStockCodeRealTimeStr(stockCode);
                StockCodeRealTimeVO stockProductVO = null;
                if (StringUtils.isBlank(stockProductVOStr)) {
                    stockProductVO = createStockProductVO(new StockCodeRealTimeVO(), orghisInfo);
                    stockProductVO.setPrevClose(BigDecimal.ZERO.toPlainString());
                } else {
                    stockProductVO = JSONObject.toJavaObject(JSONObject.parseObject(stockProductVOStr), StockCodeRealTimeVO.class);
                    Date dataDate2 = DealDateUtil.stringDateToDate(stockProductVO.getDate());
                    //日期不变化
                    if (dataDate1.compareTo(dataDate2) == 0) {
                        if(newPrice.compareTo(stockProductVO.getPrice())==0){
                            return;
                        }
                        stockProductVO.setPrice(newPrice);
                        BigDecimal newPriceBig = new BigDecimal(newPrice);
                        BigDecimal high = new BigDecimal(stockProductVO.getHigh());
                        BigDecimal low = new BigDecimal(stockProductVO.getLow());
                        if (newPriceBig.compareTo(high) >= 0) {
                            stockProductVO.setHigh(newPrice);
                        }
                        if (low.compareTo(BigDecimal.ZERO) == 0) {
                            stockProductVO.setLow(newPrice);
                        } else if (low.compareTo(newPriceBig) > 0) {
                            stockProductVO.setLow(newPrice);
                        }
                        stockProductVO.setCnyPrice(cnyPrice);
                        stockProductVO.setClosePrice(newPrice);
                        stockProductVO.setVolume(new BigDecimal(stockProductVO.getVolume()).add(volume).toPlainString());
                        //计算涨跌
                        BigDecimal change = new BigDecimal(stockProductVO.getPrice() == null ? "0" : stockProductVO.getPrice()).subtract(new BigDecimal(stockProductVO.getOpenPrice() == null ? "0" : stockProductVO.getOpenPrice())).setScale(8, RoundingMode.DOWN);
                        //计算涨跌幅
                        BigDecimal changeRate = (new BigDecimal(stockProductVO.getPrice()).subtract(new BigDecimal(stockProductVO.getOpenPrice())).setScale(8, RoundingMode.DOWN)).divide(new BigDecimal(stockProductVO.getOpenPrice()), 4, RoundingMode.HALF_UP).multiply(CommonConstant.RATE_100);
                        stockProductVO.setChange(change.toString());
                        stockProductVO.setChangeRate(changeRate.toString() + "%");
                        stockProductVO.setTime(timeStr);
                        stockProductVO.setTimestamp(String.valueOf(orghisInfo.getDateTime().getTime()));
                        //日期变化到今天
                    } else {
                        stockProductVO = createStockProductVO(new StockCodeRealTimeVO(), orghisInfo);
                        stockProductVO.setPrevClose(stockProductVO.getClosePrice());
                    }
                }

                stockProductVOStr = JSONObject.toJSONString(stockProductVO, SerializerFeature.WriteMapNullValue);
                //放入缓存 数据详情
                redisUtils.setStockCodeRealTimeStr(stockCode, stockProductVOStr);
                //推送新数据
                redisTemplate.convertAndSend(RedisKeysPrefix.VB_XEX_CHANGE_RATE_CHANNEL, stockProductVOStr);
            }catch (Exception e){
                log.info("合成实时数据错误：\n"+ ExceptionUtils.getFullStackTrace(e));
            }
        });
    }

    private StockCodeRealTimeVO createStockProductVO(StockCodeRealTimeVO stockProductVO, GoodsOrghisInfo stockOrghisInfo) {

        String newPrice = stockOrghisInfo.getOpen();
        String stockCode = stockOrghisInfo.getCode();
        Date dateNow = stockOrghisInfo.getDateTime();
        String dateStr = DealDateUtil.dateToDateString(stockOrghisInfo.getDateTime());
        String timeStr = String.valueOf(stockOrghisInfo.getTime());

        String cnyPrice = (new BigDecimal(newPrice)).setScale(2, RoundingMode.DOWN).toPlainString();

        stockProductVO.setPrice(newPrice);
        stockProductVO.setClosePrice(newPrice);
        stockProductVO.setName(stockCode);
        stockProductVO.setStockCode(stockCode);
        stockProductVO.setOpenPrice(newPrice);
        stockProductVO.setVolume(BigDecimal.ZERO.toString());
        stockProductVO.setLow(BigDecimal.ZERO.toPlainString());
        stockProductVO.setHigh(BigDecimal.ZERO.toPlainString());
        stockProductVO.setChange(BigDecimal.ZERO.toString());
        stockProductVO.setChangeRate(BigDecimal.ZERO.toString() + "%");
        stockProductVO.setCnyPrice(cnyPrice);
        stockProductVO.setTimestamp(String.valueOf(dateNow.getTime()));
        stockProductVO.setTime(timeStr);
        stockProductVO.setDate(dateStr);
        return stockProductVO;
    }

    //=====================创建锁对象=======================================

    private static final   Lock LOCK1 = new ReentrantLock();
    private static final   Lock LOCK5 = new ReentrantLock();
    private static final   Lock LOCK15 = new ReentrantLock();
    private static final   Lock LOCK30 = new ReentrantLock();
    private static final   Lock LOCK60 = new ReentrantLock();
    private static final   Lock LOCK_DAY = new ReentrantLock();

    /**
     * 数据合成走可释放线程处理
     */
    private void commonCreateData(GoodsOrghisInfo stockOrghisInfo, String  minutetype) {
        taskExecutor.execute(()-> {
            try {
                switch (minutetype) {
                    case "minute":
                        try{
                            LOCK1.lock();
                            goodsKlineMinuteInfoService.toCompoundDataMinute(stockOrghisInfo);
                        }finally {
                            LOCK1.unlock();
                        }
                        break;
                    case "minute5":
                        try{
                            LOCK5.lock();
                            goodsKlineMinute5InfoService.toCompoundData5Minute(stockOrghisInfo);
                        }finally {
                            LOCK5.unlock();
                        }
                        break;
                    case "minute15":
                        try{
                            LOCK15.lock();
                            goodsKlineMinute15InfoService.toCompoundData15Minute(stockOrghisInfo);
                        }finally {
                            LOCK15.unlock();
                        }
                        break;
                    case "minute30":
                        try{
                            LOCK30.lock();
                            goodsKlineMinute30InfoService.toCompoundData30Minute(stockOrghisInfo);
                        }finally {
                            LOCK30.unlock();
                        }
                        break;
                    case "minute60":
                        try{
                            LOCK60.lock();
                            goodsKlineMinute60InfoService.toCompoundData60Minute(stockOrghisInfo);
                        }finally {
                            LOCK60.unlock();
                        }
                        break;
                    case "day":
                        try{
                            LOCK_DAY.lock();
                            goodsKlineDayInfoService.toCompoundDataDay(stockOrghisInfo);
                        }finally {
                            LOCK_DAY.unlock();
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(minutetype + "分钟数据合成：" + ExceptionUtils.getStackTrace(e));
            }
        });
    }



}
