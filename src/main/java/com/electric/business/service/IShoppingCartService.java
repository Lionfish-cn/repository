package com.electric.business.service;

import com.electric.business.entity.ShoppingCart;
import com.electric.business.service.base.IBaseService;

import java.util.List;

public interface IShoppingCartService  extends IBaseService {
    public List<ShoppingCart> findCartsByUser(String id);
}
