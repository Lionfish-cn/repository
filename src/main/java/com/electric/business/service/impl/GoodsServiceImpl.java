package com.electric.business.service.impl;

import com.electric.business.entity.Goods;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends BaseServiceImpl implements IGoodsService {
    /**
     * 扣减库存
     */
    @Override
    public void deductGoodsStock(Goods goods, Integer deductNum) throws Exception {
        goods.setStockNumber(goods.getStockNumber() - deductNum);
        super.update(goods);
    }

    /**
     * 加回库存
     */
    @Override
    public void addBackGoodsStock(Goods goods, Integer addBackNum) {
        goods.setStockNumber(goods.getStockNumber() + addBackNum);
        super.update(goods);
    }
}
