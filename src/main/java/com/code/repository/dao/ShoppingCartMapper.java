package com.code.repository.dao;

import com.code.repository.dao.base.BaseMapper;
import com.code.repository.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper extends BaseMapper {

    List<ShoppingCart> findCartsByUser(@Param("id") String id);

}