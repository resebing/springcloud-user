package com.imooc.order.server.controller;

import com.imooc.order.server.dto.OrderDTO;
import com.imooc.order.server.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来定义SpringCloudStream的消息发送端
 */
@RestController
@RequestMapping("/send")
public class SendMessageController {
    @Autowired
    private StreamClient streamClient;


    /**
     * 消息发送
     * // 第一种发送方式 字符串
     */
//    @GetMapping("/sendMessage")
//    public void sendMessage() {

//        // 使用streamClient发送MessageBuilder构建的消息内容
//        streamClient.input().send(MessageBuilder.withPayload("发送时间:" + new Date()).build());
//    }

    /**
     * 消息发送
     * // 第一种发送方式 发送对象
     */
    @GetMapping("/sendMessage")
    public void sendMessage() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("12345");
        // 使用streamClient发送MessageBuilder构建的消息内容
        streamClient.input().send(MessageBuilder.withPayload(orderDTO).build());
    }

}
