package com.imooc.product.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
@ConfigurationProperties("girl")
public class GirlConfig {
    private String name;

    private Integer age;
}
