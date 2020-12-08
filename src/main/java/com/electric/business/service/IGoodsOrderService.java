package com.electric.business.service;

import com.electric.business.entity.GoodsOrder;
import com.electric.business.service.base.IBaseService;

import java.util.List;

public interface IGoodsOrderService extends IBaseService {
    public List<GoodsOrder> findOrdersByUser(String id);

    public Boolean cancelOrder(String id) throws Exception;
}
