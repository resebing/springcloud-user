package com.imooc.product.server.service;


import com.imooc.product.server.entity.ProductCategory;

import java.util.List;

/**
 * 类目
 */
public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
}
