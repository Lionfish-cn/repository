package com.electric.business.dao;

import com.electric.business.dao.base.BaseMapper;
import com.electric.business.entity.GoodsOrder;

import java.util.List;

public interface GoodsOrderMapper extends BaseMapper {
    List<GoodsOrder> findOrdersByUser(String userid);
}