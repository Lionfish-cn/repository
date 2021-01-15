package com.code.repository;

import com.code.repository.elastic.service.IElasticService;
import com.code.repository.entity.SystemAuth;
import com.code.repository.entity.SystemRole;
import com.code.repository.loader.MapperInitiative;
import com.code.repository.service.IGoodsService;
import com.code.repository.util.EntityUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.swing.text.html.parser.Entity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@SpringBootApplication
@MapperScan(basePackages = "com.code.repository.dao")
public class CodeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

    @Autowired
    private Environment environment;

    private Properties getProperties(){
        Properties p = new Properties();
        p.put("url",environment.getProperty("spring.datasource.url"));
        p.put("driver",environment.getProperty("spring.datasource.driver-class-name"));
        p.put("username",environment.getProperty("spring.datasource.username"));
        p.put("password",environment.getProperty("spring.datasource.password"));
        return p;
    }

    private Connection getConnection(){
        try{
            Properties p = getProperties();
            Class.forName(p.getProperty("driver"));
            return DriverManager.getConnection(p.getProperty("url"),p.getProperty("username"),p.getProperty("password"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



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

        Connection conn = getConnection();
        EntityUtil.convertModelDDL(SystemAuth.class,conn);
    }
}
