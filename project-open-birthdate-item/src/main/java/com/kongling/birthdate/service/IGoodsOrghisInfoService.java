package com.kongling.birthdate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kongling.birthdate.entity.PO.GoodsOrghisInfo;

/**
 * <p>
 * 商品实时数据表 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IGoodsOrghisInfoService extends IService<GoodsOrghisInfo> {

    /**
     * 测试 lcn
     * @return
     */
    String testLcn();
}
