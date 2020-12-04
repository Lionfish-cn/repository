package com.electric.business;

import com.electric.business.loader.MapperInitiative;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.tools.jar.CommandLine;

@SpringBootApplication
@MapperScan(basePackages = "com.electric.business.dao")
public class BusinessApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Autowired
    private MapperInitiative mapperInitiative;

    @Override
    public void run(String... args) throws Exception {
        mapperInitiative.init();
    }
}
