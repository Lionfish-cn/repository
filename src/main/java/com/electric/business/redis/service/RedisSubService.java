package com.electric.business.redis.service;

import com.electric.business.dao.GoodsMapper;
import com.electric.business.dao.GoodsOrderMapper;
import com.electric.business.dao.ShoppingCartMapper;
import com.electric.business.entity.Goods;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.service.IGoodsService;
import com.electric.business.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RedisSubService {
    @Resource
    private GoodsOrderMapper goodsOrderMapper;
    @Resource
    private IGoodsService goodsService;

    public void handleMessage(String message){
        if(!StringUtil.isNull(message)){
            try {
                String[] messages = message.split(":");
                String id = messages[messages.length-1];
                GoodsOrder goodsOrder = (GoodsOrder)goodsOrderMapper.findByPrimaryKey(id);
                goodsService.addBackGoodsStock(goodsOrder.getGoods(),goodsOrder.getBuyNumber());
                //使用jpa对数据进行删除
                goodsOrderMapper.delete(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
