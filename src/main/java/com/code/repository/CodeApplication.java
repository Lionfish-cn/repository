package com.code.repository;

import com.code.repository.elastic.service.IElasticService;
import com.code.repository.loader.MapperInitiative;
import com.code.repository.service.IGoodsService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.electric.business.dao")
public class CodeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
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
