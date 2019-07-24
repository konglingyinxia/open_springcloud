package com.kongling.platform.mapper;

import com.kongling.platform.entity.VO.homeCount.HomeCountVO;

/**
 * @author kongling
 * @package com.suda.platform.mapper
 * @date 2019-06-20  16:41
 * @project suda_cloud
 */
public interface AdminCountMapper {
    /**
     * 统计用户信息
     *
     * 及交易信息
     *
     * @return
     */
    HomeCountVO getHomePageCountInfo();
}
