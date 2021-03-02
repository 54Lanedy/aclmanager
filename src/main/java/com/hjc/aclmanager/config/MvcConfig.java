package com.hjc.aclmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置类中添加拦截器
 * Created by liyue
 * Time 2021/2/5 10:43
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login.page","/logout.page","/js/**","/plug-in/**","/favicon.ico");
    }
}
