package com.imooc.product.common.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 通用的对外提供数据的对象
 */
@Data
public class ProductInfoOutPut {
    private String productId;
    // 名字
    private String productName;

    // 单价
    private BigDecimal productPrice;

    // 库存
    private Integer productStock;

    // 描述
    private String productDescription;

    // 小图片
    private String productIcon;

    // 状态 0正常 1下架
    private Integer productStatus;

    // 类目编号
    private Integer categoryType;

}
