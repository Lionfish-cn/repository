package com.electric.business.entity.base;

public interface IBaseEntity {
    /**
     * @return ID
     */
    public abstract String getId();

    /**
     * 设置ID
     *
     * @param id
     */
    public abstract void setId(String id);

    public abstract String getInitID();


}
