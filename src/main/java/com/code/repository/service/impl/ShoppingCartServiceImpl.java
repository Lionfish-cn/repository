package com.code.repository.service.impl;

import com.code.repository.dao.ShoppingCartMapper;
import com.code.repository.entity.ShoppingCart;
import com.code.repository.service.base.BaseServiceImpl;
import com.code.repository.service.IShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ShoppingCartServiceImpl extends BaseServiceImpl implements IShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public List<ShoppingCart> findCartsByUser(String id) {
        return shoppingCartMapper.findCartsByUser(id);
    }
}
