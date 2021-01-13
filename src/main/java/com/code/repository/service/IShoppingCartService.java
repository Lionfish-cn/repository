package com.code.repository.service;

import com.code.repository.entity.ShoppingCart;
import com.code.repository.service.base.IBaseService;

import java.util.List;

public interface IShoppingCartService  extends IBaseService {
    public List<ShoppingCart> findCartsByUser(String id);
}
