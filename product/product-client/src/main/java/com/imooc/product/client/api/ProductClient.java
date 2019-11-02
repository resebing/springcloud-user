package com.imooc.product.client.api;

import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * product服务对外暴露的接口
 */
// 定义feign组件对应的服务名称,以及hystrix的降级后的回调函数所在的类
@FeignClient(name = "product", fallback = ProductClientFallback.class)
public interface ProductClient {

    /**
     * 获取商品列表（专门提供给订单服务使用）
     *
     * @param productIdList 商品id列表
     * @return 商品信息列表
     */
    @PostMapping(value = "/product/listForOrder")
    List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 根据商品id和商品数量，扣除商品库存（即下单）
     *
     * @param decreaseStockInputList 商品id和商品库存的对象
     */
    @PostMapping(value = "/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);
}
