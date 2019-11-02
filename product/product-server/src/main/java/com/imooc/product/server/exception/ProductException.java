package com.imooc.product.server.exception;


import com.imooc.product.server.enums.ResultEnum;

/**
 * 商品服务的自定义异常
 */
public class ProductException extends RuntimeException {

    private Integer code;

    private String message;


    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


}
