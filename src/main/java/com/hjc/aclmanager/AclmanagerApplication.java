package com.hjc.aclmanager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class AclmanagerApplication extends SpringBootServletInitializer {

    /**
     * 配置以war包启动springboot项目
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AclmanagerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AclmanagerApplication.class, args);
    }



}
