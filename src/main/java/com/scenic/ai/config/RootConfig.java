package com.scenic.ai.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Spring根配置类
 */
@Configuration
@ComponentScan(basePackages = {"com.scenic.ai"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {EnableWebMvc.class})
    })
@EnableTransactionManagement
@MapperScan("com.scenic.ai.mapper")
public class RootConfig {

    /**
     * 配置数据源
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("dm.jdbc.driver.DmDriver");
        config.setJdbcUrl("jdbc:dm://localhost:5236");
        config.setUsername("SYSDBA");
        config.setPassword("SYSDBA");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        return new HikariDataSource(config);
    }

    /**
     * 配置MyBatis-Plus的SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 设置MyBatis配置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        factoryBean.setTypeAliasesPackage("com.scenic.ai.model");
        
        return factoryBean.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
} 