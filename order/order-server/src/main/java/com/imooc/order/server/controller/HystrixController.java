package com.imooc.order.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * 实现Hystrix 。切记。这里不能写/
 */
@RestController
@RequestMapping("/hystrix")
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {


    /**
     * 不通过Feign组件负载均衡调用商品服务
     * 服务降级及降级的超时配置
     */
//    @HystrixCommand(commandProperties = {
//            // 设置默认的超时时间，毫秒
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
//    })
    @HystrixCommand
    @GetMapping("/getProductInfoList")
    // 设置服务降级，降级到fallback方法
    public String getProductInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://localhost:20001/product/listForOrder", Arrays.asList("157875196366160022", "157875227953464068"), String.class);
        return result;
    }


    /**
     * 不通过Feign组件负载均衡调用商品服务
     * 服务熔断
     * circuitBreaker 断路器
     */
//    @HystrixCommand(commandProperties = {
//            // 设置是否开启熔断
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//            // 断路器最小请求数量
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//            // 熔断机制开启后，休眠的时间窗口。当休眠时间窗口到期，则断路器进入半熔断。允许一次请求通过，如果调用成功，则认为服务恢复。熔断关闭。如果失败，则断路器重新进入开启状态。
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//            // 错误调用百分比。也就是最上面设置的最小请求数量的10次中发生了超过6次以上的错误。断路器进入开启状态
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
//    })
//    @GetMapping("/getProductInfoList")
//    // 设置服务降级，降级到fallback方法
//    public String getProductInfoList(@RequestParam(value = "number") Integer number) {
//        if (number % 2 == 0) {
//            return "success";
//        }
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.postForObject("http://localhost:20001/product/listForOrder", Arrays.asList("157875196366160022", "157875227953464068"), String.class);
//        return result;
//    }

    /**
     * 服务降级，降级的接口
     */
    private String fallback() {
        return "服务繁忙，请稍等";
    }

    /**
     * 服务降级，降级的接口
     */
    private String defaultFallback() {
        return "默认提示，服务反满，请稍等!";
    }
}
