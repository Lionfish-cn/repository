package com.electric.business.dao;

import com.electric.business.dao.base.BaseMapper;
import com.electric.business.entity.Pay;
import com.electric.business.entity.base.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PayMapper extends BaseMapper {
    List<BaseEntity> findByUser(@Param(value = "ID")String ID);
}