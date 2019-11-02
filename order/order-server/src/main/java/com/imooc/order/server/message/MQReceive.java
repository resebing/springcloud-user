package com.imooc.order.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收MQ消息队列到内容,接收方
 */
@Slf4j
@Component
public class MQReceive {

    /**
     * 消息接收的消息队列,将接收到到消息打印出来
     */
    // 第一种：需要手动在rabbitmq的管理面板上创建队列:  @RabbitListener(queues = "myQueue")
    // 第二种：自动创建队列:  @RabbitListener(queuesToDeclare = @Queue("autoQueue"))
    // 第三种：自动创建exchange（交换机）,然后和队列绑定
    @RabbitListener(bindings = @QueueBinding(
            // 队列
            value = @Queue("exchangeQueue"),
            // 交换机
            exchange = @Exchange("myExchange")
    ))
    public void progress(String message) {
        log.info("消息队列接收内容:{}", message);
    }


    /**
     * 假设如下两个服务，一个是水果供应商，一个数码供应商
     * 当前这个为数码供应商
     */
    @RabbitListener(bindings = @QueueBinding(
            // 交换机
            exchange = @Exchange("myOrder"),
            key = "computer",
            // 队列
            value = @Queue("computerOrder")

    ))
    public void computer(String message) {
        log.info("数码供应商队列接收内容:{}", message);
    }


    /**
     * 假设如下两个服务，一个是水果供应商，一个数码供应商
     * 当前这个为水果供应商
     */
    @RabbitListener(bindings = @QueueBinding(
            // 交换机
            exchange = @Exchange("myOrder"),
            key = "fruit",
            // 队列
            value = @Queue("fruitOrder")

    ))
    public void fruit(String message) {
        log.info("水果供应商队列接收内容:{}", message);
    }
}
