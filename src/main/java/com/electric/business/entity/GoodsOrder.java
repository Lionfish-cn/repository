package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;

import java.util.Date;
import java.util.List;

public class GoodsOrder extends BaseEntity {
    private Integer orderStatus;

    private Date goodsBuyTime;

    private Date goodsCancel;

    private Customer goodsBuyUser;

    private Double buyPrice;

    private Integer buyNumber;

    private String goodsRelateNumber;

    private String cancelReason;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus;
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

    public String getGoodsRelateNumber() {
        return goodsRelateNumber;
    }

    public void setGoodsRelateNumber(String goodsRelateNumber) {
        this.goodsRelateNumber = goodsRelateNumber;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}