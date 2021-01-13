package com.code.repository.entity;

import com.code.repository.entity.base.BaseEntity;

import java.util.Date;

public class GoodsAppraisal extends BaseEntity {

    private Goods goods;

    private String username;

    private String appraisalContent;

    private String appraisalLevel;

    private Date appraisalTime;

    private String appraisalSign;

    private String appraisalImage;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods == null ? null : goods;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getAppraisalContent() {
        return appraisalContent;
    }

    public void setAppraisalContent(String appraisalContent) {
        this.appraisalContent = appraisalContent == null ? null : appraisalContent.trim();
    }

    public String getAppraisalLevel() {
        return appraisalLevel;
    }

    public void setAppraisalLevel(String appraisalLevel) {
        this.appraisalLevel = appraisalLevel == null ? null : appraisalLevel.trim();
    }

    public Date getAppraisalTime() {
        return appraisalTime;
    }

    public void setAppraisalTime(Date appraisalTime) {
        this.appraisalTime = appraisalTime;
    }

    public String getAppraisalSign() {
        return appraisalSign;
    }

    public void setAppraisalSign(String appraisalSign) {
        this.appraisalSign = appraisalSign == null ? null : appraisalSign.trim();
    }

    public String getAppraisalImage() {
        return appraisalImage;
    }

    public void setAppraisalImage(String appraisalImage) {
        this.appraisalImage = appraisalImage == null ? null : appraisalImage.trim();
    }
}