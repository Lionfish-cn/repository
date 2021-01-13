package com.code.repository.service;

import com.code.repository.entity.GoodsOrder;
import com.code.repository.service.base.IBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IGoodsOrderService extends IBaseService {
    public List<GoodsOrder> findOrdersByUser(String id);

    public Boolean cancelOrder(String id) throws Exception;

    public void payOrders(HttpServletRequest request) throws Exception;

    public String payOrder(HttpServletRequest request) throws Exception;
}
