package com.util.convert;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author kongling
 * @package com.util.convert
 * @date 2019-07-03  11:30
 * @project suda_cloud
 */
public class JavaBeanAndMapUtil {

    /**
     *  map 转 json
     *
     * @param clazz
     * @param map
     * @param <T>
     * @return
     */
    public static <T> T mapToJavaBean(Class<T> clazz , Map map){
      return JSONObject.
              toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(map)),clazz);
    }

}
