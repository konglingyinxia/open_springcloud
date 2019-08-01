package com.kongling.platform.controller.common;

import com.kongling.platform.entity.VO.StatusVo;
import com.kongling.platform.entity.enums.rolepermission.PermissionsMenuTypeEnum;
import config.Respons.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    /**
     * 菜单类型code
     */
    @RequestMapping(value = "getMenuType", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "菜单类型code")
    public Map<String, Object> getMenuType() {
        List<StatusVo> list = new ArrayList<>();
        for (PermissionsMenuTypeEnum o : PermissionsMenuTypeEnum.values()) {
            StatusVo vo = new StatusVo();
            vo.setCode(o.getCode().intValue());
            vo.setName(o.getMessage());
            list.add(vo);
        }
        return ResponseUtil.getSuccessMap(list);
    }




}
