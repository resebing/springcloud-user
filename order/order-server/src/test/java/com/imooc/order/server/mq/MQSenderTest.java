package com.imooc.order.server.mq;

import com.imooc.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息发送者（测试）
 */
@Component
public class MQSenderTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        // 队列名称
        // 要发送到内容
        amqpTemplate.convertAndSend("exchangeQueue", "发送时间:" + new Date());
    }

    /**
     * 数码供应商的发送者
     */
    @Test
    public void computer() {
        // 队列名称
        // 要发送到内容
        amqpTemplate.convertAndSend("myOrder", "computerOrder", "发送时间:" + new Date());
    }

}
