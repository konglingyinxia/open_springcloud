package com.kongling.platform.controller.common;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共状态值
 * @author kongling
 * @package com.suda.common.controller
 * @date 2019-05-09  15:18
 * @project suda_cloud
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value = "common/status",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonStatusController {







}
