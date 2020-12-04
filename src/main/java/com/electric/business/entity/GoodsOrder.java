package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;

import java.util.Date;

public class GoodsOrder extends BaseEntity {
    private String orderStatus;

    private Goods goods;

    private Date goodsBuyTime;

    private Date goodsCancel;

    private Customer goodsBuyUser;

    private Double buyPrice;

    private Integer buyNumber;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods == null ? null : goods;
    }

    public Date getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(Date goodsBuyTime) {
        this.goodsBuyTime = goodsBuyTime;
    }

    public Date getGoodsCancel() {
        return goodsCancel;
    }

    public void setGoodsCancel(Date goodsCancel) {
        this.goodsCancel = goodsCancel;
    }

    public Customer getGoodsBuyUser() {
        return goodsBuyUser;
    }

    public void setGoodsBuyUser(Customer goodsBuyUser) {
        this.goodsBuyUser = goodsBuyUser == null ? null : goodsBuyUser;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }
}