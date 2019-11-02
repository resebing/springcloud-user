package com.imooc.order.server.controller;

import com.imooc.order.server.convert.OrderFromToOrderDTO;
import com.imooc.order.server.dto.OrderDTO;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.order.server.from.OrderFrom;
import com.imooc.order.server.service.OrderService;
import com.imooc.order.server.util.ResultUtil;
import com.imooc.order.server.vo.ResultVo;
import com.imooc.product.client.api.ProductClient;
import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    private List<ProductInfoOutPut> productInfoList;

    /**
     * 1、参数校验（参数是否正确）
     * 2、查询商品信息（调用商品服务—分布式）
     * 3、计算总价
     * 4、扣除库存（调用商品服务）
     * 5、订单入库
     * orderFrom表单内容
     * bindingResult表单认证结果
     */
    @PostMapping("/createOrder")
    // 设置跨域，并设置允许cookie的跨域
    @CrossOrigin(allowCredentials = "true")
    public ResultVo createOrder(@Valid OrderFrom orderFrom, BindingResult bindingResult) {
        // 如果表单认证错误
        if (bindingResult.hasErrors()) {
            log.error("创建订单参数不正确!");
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // 将oderFrom -->转换--->OrderDTO
        OrderDTO orderDTO = OrderFromToOrderDTO.convert(orderFrom);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单]-->购物车信息为空!");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.createOrder(orderDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultUtil.success(map);
    }


    /**
     * 第二种调用方式
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 第三种调用方式
     */
    @Autowired
    private RestTemplate myRestTemplate;


    /**
     * 第四种方式：使用Feign调用s
     */
    @Autowired
    private ProductClient productClient;

    /**
     * 用于测试调用product服务的方法
     */
    @GetMapping("/getProductMsg")
    public String msg() {
        // 第一种调用方式,直接使用RestTemplate调用服务，URL写死
//        RestTemplate restTemplate = new RestTemplate();
//        String msg = restTemplate.getForObject("http://localhost:8080/msg", String.class);
//        System.out.println(msg);

        // 第二种调用方式.先通过loadBalancerClient，通过应用名获取URL，然后再使用RestTemplate
        // 1、先选择要调用到服务的名称，可用获取任意一个Server,例如product生产者
//        ServiceInstance serviceInstance = loadBalancerClient.choose("product");
//        String url = String.format("http://%s:%s/%s", serviceInstance.getHost(), serviceInstance.getPort(), "msg");
//        RestTemplate restTemplate = new RestTemplate();
//        String msg = restTemplate.getForObject(url, String.class);
//        System.out.println(msg);


        // 第三种方式，创建RestTemplate，同时加入注解@LoadBalanced. 因为Config中使用了 @LoadBalanced注解，所以相当于RestTemplate已经具备了选择URL的功能，所以这里只要写服务的名称+要调用的接口地址
        // 注意这里的Server名称要使用大写
//        String msg = myRestTemplate.getForObject("http://PRODUCT/msg", String.class);

        // 第四种调用方式，使用Feign调用应用服务的接口
//        String msg = productClient.productMsg();
//        return msg;
        return "msg";
    }


    /**
     * 根据商品id获取商品信息列表
     */
    @GetMapping(value = "/getProductList")
    public String getProductList() {
        List<ProductInfoOutPut> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
        log.info("message -> {}", productInfoList);
        return "ok";
    }


    /**
     * 扣除商品库存,下单业务
     */
    @GetMapping(value = "/decreaseStock")
    public String decreaseStock() {
        DecreaseStockInput carDto1 = new DecreaseStockInput("157875196366160022", 3);
        DecreaseStockInput carDto2 = new DecreaseStockInput("157875227953464068", 10);
//        CarDto carDto3 = new CarDto("11111", 5);
        productClient.decreaseStock(Arrays.asList(carDto1, carDto2));
        return "ok";
    }

    /**
     * 完结订单
     */
    @PostMapping(value = "/finish")
    public ResultVo<OrderDTO> finish(@RequestParam("orderId") String orderId) {
        return ResultUtil.success(orderService.finish(orderId));
    }

}
