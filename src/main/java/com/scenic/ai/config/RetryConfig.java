package com.scenic.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;

/**
 * 重试配置类
 */
@Configuration
@EnableRetry
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 配置退避策略（指数退避）
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L); // 初始间隔1秒
        backOffPolicy.setMultiplier(2.0); // 每次间隔2倍增长
        backOffPolicy.setMaxInterval(10000L); // 最大间隔10秒
        retryTemplate.setBackOffPolicy(backOffPolicy);

        // 配置重试策略
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
            3, // 最大重试次数
            Collections.singletonMap(Exception.class, true) // 对所有异常进行重试
        );
        retryTemplate.setRetryPolicy(retryPolicy);

        // 配置超时策略
        TimeoutRetryPolicy timeoutPolicy = new TimeoutRetryPolicy();
        timeoutPolicy.setTimeout(30000L); // 30秒超时
        
        return retryTemplate;
    }
} 