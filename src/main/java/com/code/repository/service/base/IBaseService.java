package com.code.repository.service.base;

import com.code.repository.entity.base.BaseEntity;

import java.util.List;
import java.util.Map;

public interface IBaseService {
    public abstract String getModelName();
    public String save(BaseEntity baseEntity);
    public String update(BaseEntity baseEntity);
    public String delete(String id);
    public void deleteAll(String[] ids);
    public List<BaseEntity> findList(Map params);
    public BaseEntity findByPrimaryKey(String id);
}
