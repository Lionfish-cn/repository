package com.electric.business.elastic.service;

import com.electric.business.elastic.entity.ElasticEntity;

import java.util.List;

public interface IElasticService {
    public void createIndex(String idxName) throws Exception;
    public void insertBatch(List<ElasticEntity> entities);
}
