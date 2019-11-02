package com.imooc.product.server.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(1, "找不到对应的商品信息!"),
    PRODUCT_STOCK_ERROR(2, "商品库存不足!"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
