package com.imooc.product.server.repository;

import com.imooc.product.server.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {


    /**
     * 根据商品类目编号查询类目列表
     *
     * @param categoryType 类目编号
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
