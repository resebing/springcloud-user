package com.imooc.order.server.message;

import com.imooc.order.server.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * SpringCloudStream的消息接收者
 */
@Component
// 消息接收需要的注解。将接收的消息注入到StreamClient中
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    /**
//     * 定义一个接收消息的方法
//     */
//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message) {
//        log.info("Spring Cloud Stream 接收到的消息内容:{}", message.toString());
//    }
//
//    /**
//     * 定义一个接收消息的方法
//     */
//    @StreamListener(StreamClient.INPUT)
//    // 消息发送完毕后，给另一个队列发送一条消息
//    public void process(OrderDTO orderDTO) {
//        log.info("Spring Cloud Stream 接收到的消息内容:{}", orderDTO);
//    }

    /**
     * 定义一个接收消息的方法，并设置消息处理完毕后，给另一个消息队列2发送消息
     */
    @StreamListener(StreamClient.INPUT)
    // 消息发送完毕后，给另一个队列发送一条消息
    @SendTo(StreamClient.INPUT2)
    public String process(OrderDTO message) {
        log.info("Spring Cloud Stream 接收到的消息内容:{}", message);
        return "将此消息发送给消息队列2";
    }


    /**
     * 监听消息队列2  接收从INPTUT1消息队列发给消息队列2的消息
     *
     * @param message
     */
    @StreamListener(StreamClient.INPUT2)
    // 消息发送完毕后，给另一个队列发送一条消息
    public void process2(String message) {
        log.info("Spring Cloud Stream 接收到的消息内容:{}", message);
    }
}
