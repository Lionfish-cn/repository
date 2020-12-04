package com.electric.business.service.base;

import com.electric.business.entity.base.BaseEntity;

import java.util.List;
import java.util.Map;

public interface IBaseService {
    public String save(BaseEntity baseEntity);
    public String update(BaseEntity baseEntity);
    public String delete(BaseEntity baseEntity);
    public List<BaseEntity> find(BaseEntity baseEntity, Map params);
    public BaseEntity findByPrimaryKey(String id);
}
