package com.code.repository.entity.base;

import com.code.repository.util.IDGenerator;
import com.code.repository.util.StringUtil;

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

    @Override
    public String getInitID() {
        return id;
    }
}

