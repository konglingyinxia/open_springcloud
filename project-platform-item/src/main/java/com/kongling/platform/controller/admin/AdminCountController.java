package com.kongling.platform.controller.admin;

import com.kongling.platform.entity.VO.homeCount.HomeCountVO;
import com.kongling.platform.service.IAdminCountService;
import config.Respons.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 数据统计
 *
 * @author kongling
 * @package com.suda.platform.controller.admin
 * @date 2019-06-20  16:00
 * @project suda_cloud
 */
@Controller
@RequestMapping(value = "admin/countData",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminCountController {
    @Autowired
    IAdminCountService adminCountService;

    /**
     * 首页数据统计
     */
    @RequestMapping(value = "/homePageInfo")
    @ResponseBody
    public Map<String, Object> getHomePageCountInfo() {
        HomeCountVO vos = adminCountService.getHomePageCountInfo();
        return ResponseUtil.getSuccessMap(vos);
    }


}
