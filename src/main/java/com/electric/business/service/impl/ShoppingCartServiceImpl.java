package com.electric.business.service.impl;

import com.electric.business.dao.ShoppingCartMapper;
import com.electric.business.entity.ShoppingCart;
import com.electric.business.service.IShoppingCartService;
import com.electric.business.service.base.BaseServiceImpl;
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
