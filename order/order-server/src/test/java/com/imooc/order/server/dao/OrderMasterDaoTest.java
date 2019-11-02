package com.imooc.order.server.dao;

import com.imooc.order.OrderApplicationTests;
import com.imooc.order.server.entity.OrderMaster;
import com.imooc.order.server.enums.OrderStatusEnum;
import com.imooc.order.server.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderMasterDaoTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void save() {

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("13235008888");
        orderMaster.setBuyerAddress("福建省厦门市思明区");
        orderMaster.setBuyerOpenid("openId");
        orderMaster.setOrderAmount(new BigDecimal(12.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        OrderMaster result = orderMasterDao.save(orderMaster);
        Assert.assertTrue(result != null);
    }


}