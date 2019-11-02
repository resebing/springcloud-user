package com.imooc.order.server.service;

import com.imooc.order.server.dto.OrderDTO;

public interface OrderService {

    /**
     * 创建订单，订单和订单详情是一对多的的关系
     *
     * @param orderDto
     * @return
     */
    OrderDTO createOrder(OrderDTO orderDto);


    /**
     * 完结订单（只能卖家操作）
     *
     * @param orderId 订单id
     */
    OrderDTO finish(String orderId);
}
