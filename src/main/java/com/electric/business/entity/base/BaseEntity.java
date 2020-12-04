package com.electric.business.entity.base;

import com.electric.business.util.IDGenerator;
import com.electric.business.util.StringUtil;

import java.io.Serializable;

public abstract class BaseEntity implements IBaseEntity,Serializable {
    protected String id ;

    @Override
    public String getId() {
        if(StringUtil.isNull(id))
            id = IDGenerator.generateID();
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}

