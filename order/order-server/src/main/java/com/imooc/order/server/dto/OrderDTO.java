package com.imooc.order.server.dto;

import com.imooc.order.server.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家电话
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 订单openId
     */
    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，0默认新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态，0默认未支付
     */
    private Integer payStatus;

    private List<OrderDetail> orderDetailList;
}
