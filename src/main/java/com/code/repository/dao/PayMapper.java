package com.code.repository.dao;

import com.code.repository.dao.base.BaseMapper;
import com.code.repository.entity.base.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayMapper extends BaseMapper {
    List<BaseEntity> findByUser(@Param(value = "ID")String ID);
}