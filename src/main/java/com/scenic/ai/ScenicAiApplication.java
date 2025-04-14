package com.scenic.ai;

import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

/**
 * 应用程序主类
 */
@Configuration
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class ScenicAiApplication implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        // 创建Spring根上下文
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.scan("com.scenic.ai");
        
        // 注册根上下文监听器
        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        // 创建Spring MVC上下文
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.scan("com.scenic.ai.config");
        
        // 注册Spring MVC的DispatcherServlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
            "dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        // 设置默认字符编码
        servletContext.setRequestCharacterEncoding("UTF-8");
        servletContext.setResponseCharacterEncoding("UTF-8");
    }
} 