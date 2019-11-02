package com.imooc.order.server.service.impl;

import com.imooc.order.server.dao.OrderDetailDao;
import com.imooc.order.server.dao.OrderMasterDao;
import com.imooc.order.server.dto.OrderDTO;
import com.imooc.order.server.entity.OrderDetail;
import com.imooc.order.server.entity.OrderMaster;
import com.imooc.order.server.enums.OrderStatusEnum;
import com.imooc.order.server.enums.PayStatusEnum;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.order.server.service.OrderService;
import com.imooc.order.server.util.IdUtil;
import com.imooc.product.client.api.ProductClient;
import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductClient productClient;

    /**
     * 1、查询商品信息（调用商品服务—分布式）
     * 2、计算总价
     * 3、扣除库存（调用商品服务）
     * 4、订单入库
     *
     * @param orderDto
     */
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDto) {
        // 生成订单号
        String orderId = IdUtil.getUniqueId();
        //  查询商品信息（调用商品服务)
        List<String> productIdList = orderDto.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutPut> productInfoList = productClient.listForOrder(productIdList);
        //  计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            for (ProductInfoOutPut productInfoOutPut : productInfoList) {
                if (!productInfoOutPut.getProductId().equals(orderDetail.getProductId())) {
                    continue;
                }
                // 获取商品总价  单价 * 数量 然后累计add具备累计功能
                orderAmount = productInfoOutPut.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmount);
                orderDetail.setProductId(productInfoOutPut.getProductId());
                // 将商品信息 拷贝到 商品到订单详情中 注意，拷贝到过程中,null的值也会被拷贝
                BeanUtils.copyProperties(productInfoOutPut, orderDetail);
                // 所以如果不为空的设置，要等拷贝完成后，再进行设置
                orderDetail.setOrderId(orderId);
                // 设置订单详情的id
                orderDetail.setDetailId(IdUtil.getUniqueId());
                // 订单详情入库
                orderDetailDao.save(orderDetail);
            }
        }
        // 扣除库存
        // 先获取参数
        List<DecreaseStockInput> decreaseStockInputList = orderDto.getOrderDetailList().stream().map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);
        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        // copy复制到orderMaster中,注意如果orderDTO中到属性到值是null，也会拷贝
        BeanUtils.copyProperties(orderDto, orderMaster);
        // 总金额
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMasterDao.save(orderMaster);
        orderDto.setOrderId(orderMaster.getOrderId());
        return orderDto;
    }

    /**
     * 完结订单 （只能卖家操作）
     *
     * @param orderId 订单id
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 1、先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterDao.findById(orderId);
        if (!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2、查询订单状态.只有新下单才能完结
        OrderMaster orderMaster = orderMasterOptional.get();
        if (!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 3、如果订单状态没问题，则可以修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterDao.save(orderMaster);
        // 4、查询订单详情
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
