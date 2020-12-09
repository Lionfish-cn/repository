package com.electric.business.dao.base;

import com.electric.business.entity.Customer;
import com.electric.business.entity.base.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    int insertSelective(BaseEntity record);
    int updateSelective(BaseEntity baseEntity);
    List<BaseEntity> findList(Map map);
    int delete(String id);
    BaseEntity findByPrimaryKey(String id);
    BaseEntity findByUsername(@Param(value = "username")String username);
}
