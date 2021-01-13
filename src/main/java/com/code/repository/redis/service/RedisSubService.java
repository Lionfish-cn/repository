package com.code.repository.redis.service;

import com.alibaba.fastjson.JSONArray;
import com.code.repository.entity.GoodsOrder;
import com.code.repository.service.IGoodsOrderService;
import com.code.repository.service.IGoodsService;
import com.code.repository.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;


@Service
public class RedisSubService {
    @Resource
    private IGoodsOrderService goodsOrderService;
    @Resource
    private IGoodsService goodsService;

    /**
     * 若有异常就回滚
     * @param message
     */
    @Transactional(rollbackFor = RollbackException.class)
    public void handleMessage(String message){
        if(!StringUtil.isNull(message)){
            try {
                String[] messages = message.split(":");
                String id = messages[messages.length-1];
                GoodsOrder goodsOrder = (GoodsOrder)goodsOrderService.findByPrimaryKey(id);
                JSONArray arrays = JSONArray.parseArray(goodsOrder.getGoodsRelateNumber());

                goodsService.addBackGoodsStock(arrays);
                //使用jpa对数据进行删除
                goodsOrderService.delete(id);
            }catch (Exception e){
                throw new RollbackException(e);
            }
        }
    }
}
