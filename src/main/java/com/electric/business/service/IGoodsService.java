package com.electric.business.service;

import com.electric.business.entity.Goods;
import com.electric.business.service.base.IBaseService;

public interface IGoodsService extends IBaseService {
    public void deductGoodsStock(Goods goods,Integer deductNum) throws Exception;

    public void addBackGoodsStock(Goods goods,Integer addBackNum) throws Exception;
}
