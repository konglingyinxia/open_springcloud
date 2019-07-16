package com.kongling.fegin.test;

import com.constant.ServerNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kongling
 * @package com.kongling.fegin.test
 * @date 2019-07-16  14:56
 * @project suda_cloud
 */
@FeignClient(value = ServerNameConstant.PROJECT_BOURSE_DATA_KLINE)
public interface TestLcnFeginService {

    @RequestMapping(value = "/test/lcn/lcn")
    public String  testLcn();
}
