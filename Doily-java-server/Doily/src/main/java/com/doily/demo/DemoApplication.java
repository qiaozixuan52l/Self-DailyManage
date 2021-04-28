package com.doily.demo;

import com.doily.demo.doily.center.service.CenterService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.doily.demo.mapper")
@EnableScheduling
public class DemoApplication {
    public static void main(String[] args) {
        CenterService se=new CenterService();
        SpringApplication.run(DemoApplication.class, args);
    }

}
