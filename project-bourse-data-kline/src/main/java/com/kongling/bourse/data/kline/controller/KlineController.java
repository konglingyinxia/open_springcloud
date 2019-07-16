package com.kongling.bourse.data.kline.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.constant.RedisKeysPrefix;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kongling.bourse.data.kline.common.service.ICommonDataService;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.kongling.bourse.data.kline.service.IKlineService;
import com.util.DateUtil;
import com.util.DealDateUtil;
import com.util.MathUtil;
import com.util.StringUtils;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.redis.RedisUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * @author chengxuan_chen
 */
@Controller
@RequestMapping(value = "/app/dataKline",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class KlineController {

    @Autowired
    IKlineService klineService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private ICommonDataService commonDataService;

    @ResponseBody
    @RequestMapping(value = "/data", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> selectKLineData(KlineQueryVo vo, PageUtil pageUtil){
        if(StringUtils.isBlank(vo.getStockCode(),vo.getTimeCycle())){
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        PageInfo<Object> objects = klineService.listHistoryByVo(vo,pageUtil);
        return ResponseUtil.getSuccessMap(objects);
    }


    /**
     *  合成数据测试
      */
    static  long  seccond_60 = 1000*60*60*4;
    public static void main(String[] args) {

        List<String> lists = Lists.newArrayList("USDT","ETH","SDT");
        Long start = System.currentTimeMillis();
        Long num = 0L;
        while (true) {
            for (String code : lists) {
                LocalDateTime localDateTime = DealDateUtil.getUTC8LocalDateTime();

                GoodsOrghisInfo orghisInfo = new GoodsOrghisInfo();
                orghisInfo.setCode(code);
                orghisInfo.setTimestamp(localDateTime.toDate());
                orghisInfo.setTime(DealDateUtil.dateToTimeString(localDateTime.toDate()));
                orghisInfo.setDate(DealDateUtil.getYearMonthDay(localDateTime).toDate());
                orghisInfo.setDateTime(localDateTime.toDate());
                orghisInfo.setVolume("1");
                BigDecimal price = BigDecimal.ZERO;
                if (code.equalsIgnoreCase("ETH")) {
                    price = MathUtil.getOriginBoundRandom(new BigDecimal(1800), new BigDecimal(1900));
                } else if (code.equalsIgnoreCase("USDT")) {
                    price = MathUtil.getOriginBoundRandom(new BigDecimal(6), new BigDecimal(8));

                } else {
                    price = MathUtil.getOriginBoundRandom(new BigDecimal(0.9), new BigDecimal(1.2));
                }
                orghisInfo.setOpen(price.stripTrailingZeros().toPlainString());
                Jedis jedis = new Jedis("192.168.1.200",6379);
                try {

                    jedis.select(2);
                    jedis.lpush(RedisKeysPrefix.FROM_LINKED_QUEUE_NEW_DATE_MESSAGE, JSONObject.toJSONString(orghisInfo, SerializerFeature.WriteMapNullValue));

                }catch (Exception e){
                    System.out.println(ExceptionUtils.getStackTrace(e));
                }finally {
                    jedis.close();
                }
            }
            num++;
            if((System.currentTimeMillis()-start)>seccond_60){
                System.out.println(DateUtil.getCurrentDate()+":循环次数"+num);
                break;
            }
        }

    }

}
