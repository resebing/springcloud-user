package com.imooc.order.server.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    CAST_ERROR(2, "类型转换错误"),
    CART_EMPTY(3, "购物车为空"),
    ORDER_NOT_EXIST(4,"订单不存在"),
    ORDER_STATUS_ERROR(5, "订单状态错误"),
    ORDER_DETAIL_NOT_EXIST(4,"订单详情不存在"),
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
