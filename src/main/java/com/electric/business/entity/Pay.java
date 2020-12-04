package com.electric.business.entity;

import com.electric.business.entity.base.BaseEntity;

public class Pay extends BaseEntity {
    private String payType;
    private String payAccount;
    private Customer payUser;
    private Boolean payIsEnable;
    private Integer payOrder;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Customer getPayUser() {
        return payUser;
    }

    public void setPayUser(Customer payUser) {
        this.payUser = payUser;
    }

    public Boolean getPayIsEnable() {
        return payIsEnable;
    }

    public void setPayIsEnable(Boolean payIsEnable) {
        this.payIsEnable = payIsEnable;
    }

    public Integer getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(Integer payOrder) {
        this.payOrder = payOrder;
    }
}
