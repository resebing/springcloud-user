package com.imooc.order.server.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.order.server.dto.OrderDTO;
import com.imooc.order.server.entity.OrderDetail;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.order.server.from.OrderFrom;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 将orderFrom转换为OrderDTO
 */
@Slf4j
public class OrderFromToOrderDTO {

    public static OrderDTO convert(OrderFrom orderFrom) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderFrom.getName());
        orderDTO.setBuyerPhone(orderFrom.getPhone());
        orderDTO.setBuyerAddress(orderFrom.getAddress());
        orderDTO.setBuyerOpenid(orderFrom.getOpenid());
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = new Gson().fromJson(orderFrom.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("下订单时,json转换成对象失败!String={}", orderFrom.getItems());
            throw new OrderException(ResultEnum.CAST_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
