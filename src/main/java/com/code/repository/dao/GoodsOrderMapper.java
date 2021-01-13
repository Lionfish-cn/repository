package com.code.repository.dao;

import com.code.repository.entity.GoodsOrder;
import com.code.repository.dao.base.BaseMapper;

import java.util.List;

public interface GoodsOrderMapper extends BaseMapper {
    List<GoodsOrder> findOrdersByUser(String userid);
}