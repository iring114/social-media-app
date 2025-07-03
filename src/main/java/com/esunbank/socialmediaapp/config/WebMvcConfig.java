package com.esunbank.socialmediaapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置類，用於處理前端路由
 * 確保所有非API請求都轉發到index.html，以支持Vue.js的前端路由
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 將所有未映射的請求轉發到index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 靜態資源處理
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}