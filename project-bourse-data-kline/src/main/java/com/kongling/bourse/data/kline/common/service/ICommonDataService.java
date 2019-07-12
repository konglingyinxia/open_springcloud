package com.kongling.bourse.data.kline.common.service;

import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;

/**
 * @author kongling
 * @package com.kongling.bourse.data.kline.common.service
 * @date 2019-07-12  17:14
 * @project suda_cloud
 */
public interface ICommonDataService {

    /**
     * 放入实时数据
     * @param orghisInfo
     */
    public void putGoodsOrghisInfo(GoodsOrghisInfo orghisInfo);
}
