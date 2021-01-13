package com.code.repository.elastic.service;

import com.code.repository.elastic.entity.ElasticEntity;

import java.util.List;

public interface IElasticService {
    public void createIndex(String idxName) throws Exception;
    public void insertBatch(List<ElasticEntity> entities);
}
