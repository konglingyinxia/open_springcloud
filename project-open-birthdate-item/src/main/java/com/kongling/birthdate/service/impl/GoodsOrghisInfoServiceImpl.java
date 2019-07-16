package com.kongling.birthdate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.kongling.birthdate.entity.PO.GoodsOrghisInfo;
import com.kongling.birthdate.mapper.GoodsOrghisInfoMapper;
import com.kongling.birthdate.service.IGoodsOrghisInfoService;
import com.kongling.fegin.test.TestLcnFeginService;
import config.advice.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 商品实时数据表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
public class GoodsOrghisInfoServiceImpl extends ServiceImpl<GoodsOrghisInfoMapper, GoodsOrghisInfo> implements IGoodsOrghisInfoService {

    @Autowired
    TestLcnFeginService testLcnFeginService;
    /**
     * 测试分布式事务
     *
     * @return
     */
    @Override
    @Transactional
    @LcnTransaction
    public String testLcn() {
        baseMapper.insert(GoodsOrghisInfo.builder().date(new Date()).build());
        String  s=   testLcnFeginService.testLcn();
        if(0==0){
            throw new CommonException();
        }
        return s+"success:birthdate";
    }
}
