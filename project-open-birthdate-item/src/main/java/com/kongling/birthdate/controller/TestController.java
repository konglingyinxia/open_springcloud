package com.kongling.birthdate.controller;

import com.kongling.birthdate.service.IGoodsOrghisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kongling
 * @package com.kongling.birthdate.controller
 * @date 2019-07-16  14:52
 * @project suda_cloud
 */
@RestController
@RequestMapping(value = {"/test/lcn"})
public class TestController {

    @Autowired
    IGoodsOrghisInfoService goodsOrghisInfoService;

    @RequestMapping(value = {"lcn"})
    public String  testLcn(){
       return goodsOrghisInfoService.testLcn();
    }
}
