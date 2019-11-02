package com.imooc.product.server.service;


import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import com.imooc.product.server.entity.ProductInfo;

import java.util.List;

/**
 * 商品
 */
public interface ProductInfoSetvice {

    /**
     * 查询所有在货架上的商品
     */
    List<ProductInfo> findUpAll();

    /**
     * 根据商品id查询商品信息列表
     *
     * @param productIdList
     * @return
     */
    List<ProductInfoOutPut> findByProductIdListIn(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> carDtos);

}
