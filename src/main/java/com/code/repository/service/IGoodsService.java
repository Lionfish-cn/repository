package com.code.repository.service;

import com.alibaba.fastjson.JSONArray;
import com.code.repository.service.base.IBaseService;

public interface IGoodsService extends IBaseService {
    public void deductGoodsStock(JSONArray arrays) throws Exception;

    public void addBackGoodsStock(JSONArray arrays) throws Exception;
}
