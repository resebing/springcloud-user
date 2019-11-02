package com.imooc.order.server.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imooc.order.server.util.JsonUtil;
import com.imooc.product.common.dto.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接收商品服务发来的消息
 */
@Component
@Slf4j
public class ProductMQReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    /**
     * 接收从商品服务发来的消息队列
     */
    @RabbitListener(queuesToDeclare = {@Queue("productInfo")})
    public void message(String message) {
        // 将消息队列发来的对象productInfoOutPut
        List<ProductInfoOutPut> productInfoOutPutList = (List<ProductInfoOutPut>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutPut>>() {
        });
        log.info("接收从商品服务发来的对象：{}", productInfoOutPutList);
        // 接收到消息后，将消息中包含的商品库存信息保存到Redis中。将商品的id作为key，商品的数量作为value
        for (ProductInfoOutPut productInfoOutPut : productInfoOutPutList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutPut.getProductId()), String.valueOf(productInfoOutPut.getProductStock()));
        }
    }


}
