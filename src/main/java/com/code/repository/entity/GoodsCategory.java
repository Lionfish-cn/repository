package com.code.repository.entity;

import com.code.repository.entity.base.BaseEntity;

public class GoodsCategory extends BaseEntity {

    private String categoryName;

    private GoodsCategory categoryParent;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public GoodsCategory getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(GoodsCategory categoryParent) {
        this.categoryParent = categoryParent == null ? null : categoryParent;
    }
}