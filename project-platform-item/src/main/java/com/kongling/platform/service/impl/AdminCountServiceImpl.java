package com.kongling.platform.service.impl;


import com.kongling.platform.entity.VO.homeCount.HomeCountVO;
import com.kongling.platform.mapper.AdminCountMapper;
import com.kongling.platform.service.IAdminCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kongling
 * @package com.suda.platform.service.impl
 * @date 2019-06-20  16:18
 * @project suda_cloud
 */
@Service
public class AdminCountServiceImpl  implements IAdminCountService {
    @Autowired
    private AdminCountMapper adminCountMapper;

    @Override
    public HomeCountVO getHomePageCountInfo() {
        HomeCountVO vo =null;
        return vo;
    }
}
