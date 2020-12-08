package com.electric.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.electric.business.constants.EnumsConstans;
import com.electric.business.dao.GoodsOrderMapper;
import com.electric.business.entity.GoodsOrder;
import com.electric.business.service.IGoodsOrderService;
import com.electric.business.service.IGoodsService;
import com.electric.business.service.base.BaseServiceImpl;
import com.electric.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import java.util.List;

@Service
public class GoodsOrderServiceImpl extends BaseServiceImpl implements IGoodsOrderService {
    @Resource
    GoodsOrderMapper goodsOrderMapper;
    @Override
    public List<GoodsOrder> findOrdersByUser(String id) {
        return goodsOrderMapper.findOrdersByUser(id);
    }

    @Autowired
    private IGoodsService goodsService;

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public Boolean cancelOrder(String id) throws Exception {
       try {
           if(StringUtil.isNotNull(id)){
               GoodsOrder goodsOrder = (GoodsOrder)findByPrimaryKey(id);
               goodsOrder.setOrderStatus(EnumsConstans.CANCEL.ordinal());//取消状态
               goodsService.addBackGoodsStock(JSONArray.parseArray(goodsOrder.getGoodsRelateNumber()));
               String oid = update(goodsOrder);
               if(StringUtil.isNull(oid)){
                   throw new RollbackException("订单取消失败");
               }
               return true;
           }
       }catch (Exception e){
           throw new RollbackException(e);
       }
        return false;
    }
}
