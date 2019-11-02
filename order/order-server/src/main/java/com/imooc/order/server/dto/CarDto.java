package com.imooc.order.server.dto;

import lombok.Data;

/**
 * 购物车的请求的参数对象
 * 注意，作为请求参数的对象，必须要具备无参数的构造方法，否则会导致获取参数的时候的反序列化失败
 */
@Data
public class CarDto {

    /**
     * 商品id
     */
    String productId;

    /**
     * 商品数量
     */
    Integer productQuantity;

    public CarDto() {

    }

    public CarDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
