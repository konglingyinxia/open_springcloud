package com.kongling.platform.service;

import com.kongling.platform.entity.VO.homeCount.HomeCountVO;

/**
 * @author kongling
 * @package com.suda.platform.service
 * @date 2019-06-20  16:17
 * @project suda_cloud
 */
public interface IAdminCountService {
    /**
     * 获取首页统计信息
     *
     * @return
     */
    HomeCountVO getHomePageCountInfo();
}
