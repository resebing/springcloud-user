package com.imooc.product.server.service.impl;

import com.imooc.product.server.entity.ProductCategory;
import com.imooc.product.server.repository.ProductCategoryDao;
import com.imooc.product.server.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> list) {
        return productCategoryDao.findByCategoryTypeIn(list);
    }
}
