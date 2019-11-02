package com.imooc.product.server.controller;

import com.imooc.product.common.dti.DecreaseStockInput;
import com.imooc.product.common.dto.ProductInfoOutPut;
import com.imooc.product.server.entity.ProductCategory;
import com.imooc.product.server.entity.ProductInfo;
import com.imooc.product.server.repository.ProductInfoDao;
import com.imooc.product.server.service.ProductCategoryService;
import com.imooc.product.server.service.ProductInfoSetvice;
import com.imooc.product.server.util.ResultUtil;
import com.imooc.product.server.vo.ProductInfoVo;
import com.imooc.product.server.vo.ProductVo;
import com.imooc.product.server.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoSetvice productInfoSetvice;
    @Autowired
    private ProductInfoDao productInfoDao;

    /**
     * 1、查询所有在货架上到商品
     * 2、获取类目type的列表
     * 3、查询类目
     * 4、构造数据
     */
    @GetMapping("/list")
    public Result list() throws InterruptedException {
        Thread.sleep(3000);
        //1、查询所有在货架上到商品
        List<ProductInfo> productInfoList = productInfoSetvice.findUpAll();
        //2、获取类目type的列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //3、查询类目
        List<ProductCategory> productCategories = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //4、构造数据
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (!Objects.equals(productInfo.getCategoryType(), productCategory.getCategoryType())) {
                    continue;
                }
                ProductInfoVo productInfoVo = new ProductInfoVo();
                BeanUtils.copyProperties(productInfo, productInfoVo);
//                productInfoVo.setProductId(productInfo.getProductId());
//                productInfoVo.setProductName(productInfo.getProductName());
//                productInfoVo.setProductPrice(productInfo.getProductPrice());
//                productInfoVo.setProductIcon(productInfo.getProductIcon());
//                productInfoVo.setProductDescription(productInfo.getProductDescription());
                productInfoVos.add(productInfoVo);
            }
            productVo.setProductInfoVoList(productInfoVos);
            productVoList.add(productVo);
        }
        return ResultUtil.success(productVoList);
    }

    /**
     * 获取商品列表（专门提供给订单服务使用）
     *
     * @param productIdList 商品id列表
     * @return 商品信息列表
     */
    @PostMapping(value = "/listForOrder")
    public List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList) {
        return productInfoSetvice.findByProductIdListIn(productIdList);
    }


    /**
     * 根据商品id和商品数量，扣除商品库存（即下单）
     *
     * @param decreaseStockInputList 商品id和商品库存的对象
     */
    @PostMapping(value = "/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productInfoSetvice.decreaseStock(decreaseStockInputList);
    }


    /**
     * 测试cookie是否可以传递
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/gateway")
    public String gateway(HttpServletRequest request) {
        return Arrays.toString(request.getCookies());
    }
}
