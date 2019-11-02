package com.imooc.order.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * SpringCloudStream结合RabbitMQ的使用。先定一个input和output
 */
public interface StreamClient {

    /**
     * 输入和输出用的是同一个队列。INPUT就是队列的名称和交换机的名称
     */
    public static final String INPUT = "myMessage";
    /**
     * 输入和输出用的是同一个队列。INPUT就是队列的名称和交换机的名称
     */
    public static final String INPUT2 = "myMessage2";

    /**
     * 自定义的SpringCloudStream的Input
     */
    @Input(INPUT)
    SubscribableChannel input();

    /**
     * 自定义的SpringCloudStream的Output
     */
    @Output(INPUT2)
    MessageChannel output();

//    /**
//     * 自定义的SpringCloudStream的Input
//     */
//    @Input(INPUT2)
//    SubscribableChannel input2();
//
//    /**
//     * 自定义的SpringCloudStream的Output
//     */
//    @Output(INPUT2)
//    MessageChannel output2();


}
