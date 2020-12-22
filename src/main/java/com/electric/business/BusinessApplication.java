package com.electric.business;

import com.electric.business.elastic.entity.ElasticEntity;
import com.electric.business.elastic.service.IElasticService;
import com.electric.business.email.controller.EmailController;
import com.electric.business.entity.base.BaseEntity;
import com.electric.business.loader.MapperInitiative;
import com.electric.business.service.IGoodsService;
import com.electric.business.util.EntityUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.electric.business.dao")
public class BusinessApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Autowired
    private MapperInitiative mapperInitiative;

    @Autowired
    private IElasticService elasticService;

    @Autowired
    private IGoodsService goodsService;

    @Override
    public void run(String... args) throws Exception {
//        mapperInitiative.init();
//        List<BaseEntity> baseEntities = goodsService.findList(null);
//        List<ElasticEntity> elasticEntities = new ArrayList<>();
//        baseEntities.forEach(baseEntity -> {
//            try {
//                String idx = EntityUtil.getSimpleName(baseEntity.getClass().getName());
//                elasticService.createIndex(idx);
//                ElasticEntity elasticEntity = ElasticEntity.builder().id(baseEntity.getId()).indices(idx).
//                        source(EntityUtil.convertModelToJson(baseEntity).toJSONString()).opType("create").build();
//                elasticEntities.add(elasticEntity);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        elasticService.insertBatch(elasticEntities);

//        EmailController emailController = new EmailController();
//        emailController.parseEmail();
    }
}
