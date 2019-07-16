package com.kongling.bourse.data.kline.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TxTransaction;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import com.kongling.bourse.data.kline.mapper.GoodsOrghisInfoMapper;
import com.kongling.bourse.data.kline.service.IGoodsOrghisInfoService;
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

    @Transactional(rollbackFor = {})
    @TxTransaction(propagation = DTXPropagation.SUPPORTS)
    @Override
    public String testLcn() {
        baseMapper.insert(GoodsOrghisInfo.builder().date(new Date()).build());
        return "success:data";
    }
}
