package com.imooc.order.server.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 使用自定义注解的方式实现，LoadBalenced，第三种调用方式采用的
 */
@Component
public class RestTemplateConfig {

    // Spring加载对象
    @Bean
    // 获取服务的URL的注解
    @LoadBalanced
    public RestTemplate myRestTemplate() {
        return new RestTemplate();
    }

}
