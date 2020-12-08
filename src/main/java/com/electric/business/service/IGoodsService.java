package com.electric.business.service;

import com.alibaba.fastjson.JSONArray;
import com.electric.business.entity.Goods;
import com.electric.business.service.base.IBaseService;

import java.util.List;

public interface IGoodsService extends IBaseService {
    public void deductGoodsStock(JSONArray arrays) throws Exception;

    public void addBackGoodsStock(JSONArray arrays) throws Exception;
}
