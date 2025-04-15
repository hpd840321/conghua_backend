package com.scenic.ai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);
    
    @Value("${spring.mvc.view.prefix:/WEB-INF/jsp/}")
    private String viewPrefix;

    @Value("${spring.mvc.view.suffix:.jsp}")
    private String viewSuffix;

    @Value("${spring.web.resources.static-locations:classpath:/static/,classpath:/META-INF/resources/}")
    private String[] staticLocations;
    
    @Bean
    public InternalResourceViewResolver viewResolver() {
        log.info("Initializing view resolver");
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix(viewPrefix);
        resolver.setSuffix(viewSuffix);
        resolver.setOrder(1);
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setExposedContextBeanNames("springMacroRequestContext");
        
        log.info("View resolver configuration:");
        log.info("+ View Prefix: {}", viewPrefix);
        log.info("+ View Suffix: {}", viewSuffix);
        
        return resolver;
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        log.debug("Configuring view resolver registry");
        registry.viewResolver(viewResolver());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("Configuring resource handlers");
        
        // 静态资源路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations(staticLocations)
                .setCachePeriod(3600);
        
        // JSP资源路径 - 使用META-INF/resources目录
        registry.addResourceHandler("/WEB-INF/jsp/**")
                .addResourceLocations("classpath:/META-INF/resources/WEB-INF/jsp/")
                .setCachePeriod(0);
        
        log.info("Resource mapping configuration:");
        log.info("+ Static Resource Locations: {}", String.join(", ", staticLocations));
        log.info("+ JSP Resource Location: classpath:/META-INF/resources/WEB-INF/jsp/");
    }

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 配置消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    }

    /**
     * 配置ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 注册Java 8时间模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        objectMapper.registerModule(javaTimeModule);
        
        // 注册自定义模块
        SimpleModule simpleModule = new SimpleModule();
        objectMapper.registerModule(simpleModule);
        
        return objectMapper;
    }
} 