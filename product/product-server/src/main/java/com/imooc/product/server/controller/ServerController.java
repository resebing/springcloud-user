package com.imooc.product.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试跨服务调用是否可用的服务
 */
@RestController
public class ServerController {


    @GetMapping("/msg")
    public String message() {
        System.out.println("接口被调用了......");
        String message = "this is product msg 2";
        return message;
    }
}
