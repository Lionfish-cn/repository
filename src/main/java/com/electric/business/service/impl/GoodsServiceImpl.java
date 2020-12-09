package com.electric.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.electric.business.entity.Goods;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends BaseServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsService goodsService;

    /**
     * 扣减库存
     */
    @Override
    public  void deductGoodsStock(JSONArray arrGoods) throws Exception {
        modifyGoodsStock(arrGoods, "debuct");
    }

    /**
     * 加回库存
     */
    @Override
    public void addBackGoodsStock(JSONArray arrGoods) {
        modifyGoodsStock(arrGoods, "back");
    }

    /**
     * 修改库存设置为
     * @param arrGoods
     * @param type
     */
    public synchronized void modifyGoodsStock(JSONArray arrGoods, String type) {
        for (int i = 0; i < arrGoods.size(); i++) {
            JSONObject _obj = arrGoods.getJSONObject(i);
            Goods goods = (Goods) goodsService.findByPrimaryKey(_obj.getString("goodsid"));
            Integer deductNum = _obj.getInteger("buynum");
            if("back".equals(type)){
                goods.setStockNumber(goods.getStockNumber() + deductNum);
            }else{
                goods.setStockNumber(goods.getStockNumber() - deductNum);
            }

            super.update(goods);
        }
    }
}
