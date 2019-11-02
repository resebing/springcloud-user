//package com.imooc.order.server.client;
//
//import com.imooc.order.server.dataobject.ProductInfo;
//import com.imooc.order.server.dto.CarDto;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
///**
// * 定义要使用Feign调用的接口
// */
//// 表明是Feign的接口，要调用哪个应用,name表示应用的名称
//@FeignClient(name = "product")
//public interface ProductClient {
//
//    // 接口地址要和要调用的接口地址一样
//    @GetMapping("/msg")
//    // 方法名称可以不和接口地址一样
//    String productMsg();
//
//    /**
//     * 注意1:这个路径要填写完整的路径。
//     * 注意2:使用List传输数据的时候，不能使用GetMapping，要使用PostMapping
//     * 获取商品信息列表
//     */
//    @PostMapping("/product/listForOrder")
//    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);
//
//
//    @PostMapping("/product/decreaseStock")
//    void decreaseStock(@RequestBody List<CarDto> carDtoLists);
//
//}
