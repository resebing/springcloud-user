package com.imooc.product.server.repository;

import com.imooc.product.server.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {

    /**
     * 查询在货架上的商品
     *
     * @param productStatus 0正常 1下架
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 根据商品id列表查询商品(使用In查询）
     *
     * @param productIdList 商品id列表
     */
    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
