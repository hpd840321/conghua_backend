package com.scenic.ai.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

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
@PropertySource("classpath:jdbc.properties")
public class RootConfig {

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.pool.initialSize}")
    private int initialSize;

    @Value("${jdbc.pool.minIdle}")
    private int minIdle;

    @Value("${jdbc.pool.maxActive}")
    private int maxActive;

    @Value("${jdbc.pool.maxWait}")
    private int maxWait;

    /**
     * 配置数据源
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxActive);
        config.setMinimumIdle(minIdle);
        config.setConnectionTimeout(maxWait);
        config.setAutoCommit(true);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1200000);
        config.setConnectionTestQuery("SELECT 1");
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