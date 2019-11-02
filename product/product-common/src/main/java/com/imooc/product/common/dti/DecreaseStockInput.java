package com.imooc.product.common.dti;

import lombok.Data;

/**
 * 购物车的请求的参数对象
 */
@Data
public class DecreaseStockInput {

    /**
     * 商品id
     */
    String productId;

    /**
     * 商品数量
     */
    Integer productQuantity;

    public DecreaseStockInput() {

    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
