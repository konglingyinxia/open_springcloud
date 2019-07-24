package config.redis;

import com.constant.CommonConstant;
import com.constant.RedisKeysPrefix;
import com.google.common.collect.Maps;
import config.com.MyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author kongling
 * @package com.suda.server
 * @date 2019-05-14  14:57
 * @project suda_cloud
 */
@Component
@Slf4j
public class RedisUtils {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplate1")
    private RedisTemplate redisTemplate1;
    @Resource(name = "stringRedisTemplate1")
    private StringRedisTemplate stringRedisTemplate1;

    @Resource(name = "redisTemplate2")
    private RedisTemplate redisTemplate2;

    @Resource(name = "stringRedisTemplate2")
    private StringRedisTemplate stringRedisTemplate2;


    @Resource(name = "redisTemplate3")
    private RedisTemplate redisTemplate3;
    @Resource(name = "stringRedisTemplate3")
    private StringRedisTemplate stringRedisTemplate3;



    @Autowired
    MyConfiguration myConfiguration;


    /**
     * 设置hash key
     */
    public void setRedisHash(String redisKey, Long mapKey, String mapValue) {
        redisTemplate.opsForHash().put(redisKey, mapKey, mapValue);
    }

    /**
     * 获取一个hash map
     */
    public Map getRedisHashValue(String redisKey, long mapKey) {
        return (Map) redisTemplate.opsForHash().get(redisKey, mapKey);
    }
    /**
     * 获取所有hash maps
     */
    public Map getRedisHashValues(String redisKey) {
        return  redisTemplate.opsForHash().entries(redisKey);
    }

    /**
     * 删除key
     */
    public void deleteRedisHashKey(String redisKey, long mapKey) {
        redisTemplate.opsForHash().delete(redisKey, mapKey);
    }

    /**
     * 设置短信信息key
     */
    public void setSmsRedisHashValue(Byte codeType,String account,String code){
        redisTemplate.opsForValue().set(
                RedisKeysPrefix.SMS_SEND_TIME_MESSAGE+account+code,code, CommonConstant.VALIDITY_CODE_TIMEOUT, TimeUnit.MILLISECONDS);
        //发送短信时间间隔
        redisTemplate.opsForValue().set(
                RedisKeysPrefix.SMS_SEND_TIME_MESSAGE+account+codeType,code, CommonConstant.VALIDITY_CODE_INTERVAL_TIMEOUT_1, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取单个短信验证码信息
     */
    public Object getSmsRedisMessage(String account,String code){
      return   redisTemplate.opsForValue().get(RedisKeysPrefix.SMS_SEND_TIME_MESSAGE+account+code);
    }
    /**
     * 发送短信时间间隔是否够一分钟
     */
    public Boolean  isCanSendTwoSms(String account,Byte codeType){
        Boolean can = false;
        Object   o = redisTemplate.opsForValue().get(RedisKeysPrefix.SMS_SEND_TIME_MESSAGE+account+codeType);
         if(o==null){
            can=true;
         }
        return can;
    }
//==================================数据币种信息=========================================================================
    private  static ConcurrentMap maps = Maps.newConcurrentMap();
    /**
     * 获取单个币种涨跌信息
     */
    public String getStockCodeRealTimeStr(String stockCode){
       Object o=
       stringRedisTemplate.opsForValue().get(RedisKeysPrefix.VB_XEX_CHANGE_RATE_DATA+stockCode);
        String str = null;
        if(o!=null){
            str = String.valueOf(o);
        }
        return str;
    }

    /**
     * 设置单个币种涨跌信息
     */
    public void setStockCodeRealTimeStr(String stockCode,String data){
        stringRedisTemplate.opsForValue().set(RedisKeysPrefix.VB_XEX_CHANGE_RATE_DATA+stockCode,data);
    }


    /**
     * 加入队列
     */
    public void lpushQueue(String key,Object object){
        stringRedisTemplate2.opsForList().leftPush(key,object.toString());
    }


    /**
     * 从队列中取出数据
     */
    public String rpopQueue(String key){
        try{
            return  stringRedisTemplate2.opsForList().rightPop(key,1L, TimeUnit.SECONDS);
        }catch (Exception e){
            log.info("取出队列失败：\n"+ e.getMessage());
        }
        return null;
    }

//=====================用户信息存储================================================================================

    /**
     * 存储app用户信息
     */
    @Async
    public  void setStorageStockUser(Long id,String str){
        stringRedisTemplate3.opsForValue().set(RedisKeysPrefix.APP_USER_CACHE_EDIT_KEY+id,str);
    }
    /**
     * 获取app 用户信息
     */
    public  String getStorageStockUser(Long id){
        return stringRedisTemplate3.opsForValue().get(RedisKeysPrefix.APP_USER_CACHE_EDIT_KEY+id);
    }


    /**
     * 存储admin 用户信息
     */
    @Async
    public  void setStorageAdminUser(Long id,String str){
        stringRedisTemplate3.opsForValue().set(RedisKeysPrefix.ADMIN_USER_CACHE_EDIT_KEY+id,str);
    }
    /**
     * 获取admin 用户信息
     */
    public  String getStorageAdminUser(Long id){
        return stringRedisTemplate3.opsForValue().get(RedisKeysPrefix.ADMIN_USER_CACHE_EDIT_KEY+id);
    }
    //===================================角色权限信息==================================================================
    /**
     * 存储角色权限信息
     */
    @Async
    public  void setStorageRolePermission(Long roleId,String str){
        stringRedisTemplate3.opsForSet().add(RedisKeysPrefix.ADMIN_USER_ROLE_PERMISSION_EDIT_KEY+roleId,str);
    }

    /**
     * 查询角色权限状态
     */
    public  Boolean rolePermissionBooleanIs(Long roleId,String str){
        return stringRedisTemplate3.opsForSet().isMember(RedisKeysPrefix.ADMIN_USER_ROLE_PERMISSION_EDIT_KEY+roleId,str);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.select(3);
        System.out.println(jedis.get(RedisKeysPrefix.ADMIN_USER_ROLE_PERMISSION_EDIT_KEY+1));

    }

    //============================================汽车大亨游戏升级记录存储 redis======================
    /**
     * 设置hash key
     */
    public void setCarGameUpdateMaps(Long mapKey, Object mapValue) {
        redisTemplate3.opsForHash().put(getCarGameUpdateListKey(), mapKey, mapValue);
    }

    /**
     * 获取一个hash map
     */
    public Object getCarGameUpdateMaps() {
        return  redisTemplate.opsForHash().entries( getCarGameUpdateListKey());
    }

    /**
     * 删除升级失效产能key
     * @return
     */
    @Async
    public void delCarGameUpdateMaps(Long mapKey) {
        redisTemplate.opsForHash().delete( getCarGameUpdateListKey(), mapKey);
    }
    private String  getCarGameUpdateListKey(){
        return RedisKeysPrefix.STOCK_USER_BUILDINGS_CAPACITY_RECORD_LISTS;
    }

    /**
     * 获取产出时间
     */
    public String getCarGameOptputTimeStr(Long id){
        Object o=stringRedisTemplate.opsForValue().get(getCarGameOptputTimeStrKey(id));
        String str = null;
        if(o!=null){
            str = String.valueOf(o);
        }
        return str;
    }
    /**
     * 保存每次产出时间
     */
    public void setCarGameOptputTimeStr(Long id,String data){
        stringRedisTemplate.opsForValue().set(getCarGameOptputTimeStrKey(id),data);
    }

    /**
     * 删除失效记录的产出时间
     * @param id
     * @return
     */
    public void delCarGameOptputTimeStr(Long id){
        stringRedisTemplate.delete(getCarGameOptputTimeStrKey(id));
    }
    private String  getCarGameOptputTimeStrKey(Long id){
        return RedisKeysPrefix.STOCK_USER_BUILDINGS_CAPACITY_OUTPUT_TIME_VALUE+id;
    }


    //=======================================商城区域地址缓存=============================================================
    /**
     * 获取商城区域地址
     */
    public Object getShopArea(){
        Object o=redisTemplate1.opsForValue().get(getShopAreaKey());

        return o;
    }
    /**
     * 保存商城区域地址
     */
    public void setShopArea(Object o){
        redisTemplate1.opsForValue().set(getShopAreaKey(),o);
    }

    /**
     * 商城区域 key
     * @return
     */
    private String  getShopAreaKey(){
        return RedisKeysPrefix.SUDA_SHOP_AREA_KEY;
    }


}
