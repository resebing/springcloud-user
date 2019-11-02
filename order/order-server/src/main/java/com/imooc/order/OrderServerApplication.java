package com.imooc.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
//// 将当前的订单服务注册到注册中心
//@EnableDiscoveryClient
//// Hystrix服务容错的注解
//@EnableCircuitBreaker
// 指定productClient所在的目录
@EnableFeignClients(basePackages = "com.imooc.product.client")
@EnableConfigurationProperties
@SpringCloudApplication
// 设置要扫描加入到Spring容器对象（因为引入的productClientFallbackz这个类是加了Component这个注解。要去扫描，才能注入到当前项目的容器里面的
@ComponentScan(basePackages = "com.imooc")
// 开启hystrix的数据报
@EnableHystrixDashboard
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}
