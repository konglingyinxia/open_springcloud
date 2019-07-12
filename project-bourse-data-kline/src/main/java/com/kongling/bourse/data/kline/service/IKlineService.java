package com.kongling.bourse.data.kline.service;

import com.github.pagehelper.PageInfo;
import com.kongling.bourse.data.kline.entity.VO.KlineQueryVo;
import com.util.pageinfoutil.PageUtil;

/**
 * @author kongling
 * @package com.kongling.bourse.data.kline.service
 * @date 2019-05-28  10:36
 * @project suda_cloud
 */
public interface IKlineService {
    /**
     * 查询K线
     * @param vo
     * @param pageUtil
     * @return
     */
    PageInfo<Object> listHistoryByVo(KlineQueryVo vo, PageUtil pageUtil);
}
