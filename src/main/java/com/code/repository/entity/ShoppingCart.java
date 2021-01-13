package com.code.repository.entity;

import com.code.repository.entity.base.BaseEntity;

public class ShoppingCart extends BaseEntity {
    private Goods goods;

    private Integer buyNumber;

    private Customer addUser;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods == null ? null : goods;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Customer getAddUser() {
        return addUser;
    }

    public void setAddUser(Customer addUser) {
        this.addUser = addUser == null ? null : addUser;
    }
}