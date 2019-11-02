package com.imooc.order.server.dao;

import com.imooc.order.OrderApplicationTests;
import com.imooc.order.server.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderDetailDaoTest extends OrderApplicationTests {


    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void save() {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("123");
        orderDetail.setProductIcon("http://fuss10.elemecdn.com/0/49/65d10ef215d3c770ebb2b5ea962a7jpeg.jpeg");
        orderDetail.setProductId("157875196366160022");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(0.01));
        orderDetail.setProductQuantity(12);
        OrderDetail result = orderDetailDao.save(orderDetail);
        Assert.assertTrue(result != null);
    }

}