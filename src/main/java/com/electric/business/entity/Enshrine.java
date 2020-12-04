package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;

public class Enshrine extends BaseEntity {

    private Goods goods;

    private String enshrineUser;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods == null ? null : goods;
    }

    public String getEnshrineUser() {
        return enshrineUser;
    }

    public void setEnshrineUser(String enshrineUser) {
        this.enshrineUser = enshrineUser == null ? null : enshrineUser.trim();
    }
}