package com.imooc.product.server.controller;

import com.imooc.product.server.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/env")
// 加上刷新配置文件内容的注解
@RefreshScope
public class EnvController {

    /**
     * 测试打印是否真的下载了配置文件
     */
    @Value("${env}")
    private String env;


    /**
     * 打印内容
     *
     * @return
     */
    @GetMapping("/print")
    public String print() {
        System.out.println(env);
        return env;
    }


    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/gril/print")
    public String grilPrint() {
        return girlConfig.getName();
    }

}
