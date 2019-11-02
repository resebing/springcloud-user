package com.imooc.product.server.service.impl;

import com.google.gson.Gson;
import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import com.imooc.product.server.entity.ProductInfo;
import com.imooc.product.server.enums.ProductStatusEnum;
import com.imooc.product.server.enums.ResultEnum;
import com.imooc.product.server.exception.ProductException;
import com.imooc.product.server.repository.ProductInfoDao;
import com.imooc.product.server.service.ProductInfoSetvice;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 商品
 */
@Service
public class ProductInfoSetviceImpl implements ProductInfoSetvice {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }


    /**
     * @param productIdList
     * @return
     */
    @Override
    public List<ProductInfoOutPut> findByProductIdListIn(List<String> productIdList) {
        List<ProductInfo> productInfoList = productInfoDao.findByProductIdIn(productIdList);
        return productInfoList.stream().map(x -> {
            ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
            BeanUtils.copyProperties(x, productInfoOutPut);
            return productInfoOutPut;
        }).collect(Collectors.toList());
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 商品下单，扣库存
     *
     * @param carDtos 商品下单的参数，包括商品id和数量
     */
    @Override
    public void decreaseStock(List<DecreaseStockInput> carDtos) {
        List<ProductInfo> productInfoList = decreaseStockProcess(carDtos);
        // 扣除库存后，发送消息队列
        List<ProductInfoOutPut> productInfoOutPutList = productInfoList.stream().map(e -> {
            ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
            BeanUtils.copyProperties(e, productInfoOutPut);
            return productInfoOutPut;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", new Gson().toJson(productInfoOutPutList));
    }

    /**
     * 操作商品扣除库存，有可能存在抛出异常，导致事务回滚的情况，所以要将发送消息的步骤放在全部处理完成后
     *
     * @param carDtos
     * @return
     */
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> carDtos) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput : carDtos) {
            Optional<ProductInfo> optionalProductInfo = productInfoDao.findById(decreaseStockInput.getProductId());
            // 如果商品不存在，则抛出商品不存在的自定义异常
            if (!optionalProductInfo.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 如果商品存在的话，需要判断商品是否够扣除s
            ProductInfo productInfo = optionalProductInfo.get();
            int stock = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            // 如果商品库存不足，则抛出商品库存不足的异常
            if (stock < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            // 扣除商品库存
            productInfo.setProductStock(stock);
            productInfoDao.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
