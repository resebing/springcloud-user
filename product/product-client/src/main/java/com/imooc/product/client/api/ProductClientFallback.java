package com.imooc.product.client.api;


import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现回调函数。指定一旦出现服务器降级后，访问的方法。如下，设置返回null
 */
@Component
public class ProductClientFallback implements ProductClient {


    @Override
    public List<ProductInfoOutPut> listForOrder(List<String> productIdList) {
        return null;
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

    }
}
