package com.imooc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
// 将统一配置中心注册到注册中心中
// EnableDiscoveryClient可以发现多种服务，包括Eureka、Zookeeper。而EnableEurekaClient只能是Eureka
@EnableDiscoveryClient
// 将当前项目变为configServer.SpringCloud的统一配置中心
@EnableConfigServer
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
