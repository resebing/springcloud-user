package com.imooc.order.server.dao;

import com.imooc.order.server.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {



}
