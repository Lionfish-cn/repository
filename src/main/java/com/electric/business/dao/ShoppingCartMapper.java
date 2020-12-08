package com.electric.business.dao;

import com.electric.business.dao.base.BaseMapper;
import com.electric.business.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper extends BaseMapper {

    List<ShoppingCart> findCartsByUser(@Param("id") String id);

}