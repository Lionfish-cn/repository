package com.electric.business.elastic.service;


import com.electric.business.elastic.entity.ElasticEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ElasticService implements IElasticService {

    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    @Override
    public void createIndex(String idx) {
        try {
            if (!this.indexExist(idx)) {
                log.info("index [" + idx + "] is exist!");
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idx);
            buildSettings(request);
            CreateIndexResponse res = restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);

            if(!res.isAcknowledged()){
                log.info("Create indices is failure!");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量创建文档
     * @param entities
     */
    @Override
    public void insertBatch(List<ElasticEntity> entities) {
        BulkRequest bulk = new BulkRequest();
        entities.forEach(entity ->{
            bulk.add(new IndexRequest(entity.getIndices()).id(entity.getId()).
                    opType(entity.getOpType()).source(entity.getSource(),XContentType.JSON));
        });
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulk,RequestOptions.DEFAULT);
            if(!bulkItemResponses.hasFailures()){
                BulkItemResponse[] items = bulkItemResponses.getItems();
                for (BulkItemResponse item : items) {
                    DocWriteResponse writeResponse = item.getResponse();
                    log.info("success create docs,the message is "+writeResponse.getResult().getLowercase());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建一个文档
     * @param elasticEntity
     */
    public void insertOrUpdate(ElasticEntity elasticEntity){
        log.error("Data idx:{},entity:{}",elasticEntity.getIndices(),elasticEntity.getSource());
        IndexRequest request = new IndexRequest(elasticEntity.getIndices());
        request.id(elasticEntity.getId());
        request.opType(elasticEntity.getOpType());
        request.source(elasticEntity.getSource(),XContentType.JSON);
        try{
            restHighLevelClient.index(request,RequestOptions.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 批量删除 TODO
     * @param idx
     * @param ids
     */
    public void deleteBatch(String idx,List<String> ids){
        BulkRequest bulkRequest = new BulkRequest();
        ids.forEach(id -> {
            bulkRequest.add(new DeleteRequest(idx,id));
        });
        try{
            restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void search(){
        SearchRequest searchRequest = new SearchRequest();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("field","value");
    }


    /**
     * 配置索引
     * @param request
     */
    public void buildSettings(CreateIndexRequest request) {
        //number_of_shards : 每个主分片区的个数 默认个数是5
        //number_of_replicas : 副本的个数
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2).build());
    }

    /**
     * 判断索引是否存在
     *
     * @param idx
     * @return
     */
    public boolean indexExist(String idx) {
        try {
            GetIndexRequest request = new GetIndexRequest(idx);
            request.local(false);
            request.humanReadable(false);
            request.includeDefaults(false);
            request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
