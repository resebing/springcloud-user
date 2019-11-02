package com.imooc.product.server.vo;

import lombok.Data;

/**
 * HTTP请求的数据最外层对象
 * @param <T>
 */
@Data
public class Result<T> {

    /**
     * 回执编码
     */
    private Integer code;
    /**
     * 消息提示
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
}
