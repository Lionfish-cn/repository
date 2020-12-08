package com.electric.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.entity.Goods;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends BaseServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsService goodsService;
    /**
     * 扣减库存
     */
    @Override
    public void deductGoodsStock(JSONArray arrGoods) throws Exception {
        for(int i=0;i<arrGoods.size();i++){
            JSONObject _obj = arrGoods.getJSONObject(i);
            Goods goods = (Goods)goodsService.findByPrimaryKey(_obj.getString("goodsid"));
            Integer deductNum = _obj.getInteger("buynum");
            goods.setStockNumber(goods.getStockNumber() - deductNum);
            super.update(goods);
        }
    }

    /**
     * 加回库存
     */
    @Override
    public void addBackGoodsStock(JSONArray arrGoods) {
        for(int i=0;i<arrGoods.size();i++){
            JSONObject _obj = arrGoods.getJSONObject(i);
            Goods goods = (Goods)goodsService.findByPrimaryKey(_obj.getString("goodsid"));
            Integer deductNum = _obj.getInteger("buynum");
            goods.setStockNumber(goods.getStockNumber() - deductNum);
            super.update(goods);
        }
    }
}
