package com.imooc.product.server.util;


import com.imooc.product.server.vo.Result;

public class ResultUtil {
    public static Result success(Object data) {
        Result<Object> result = new Result<>();
        result.setCode(0);
        result.setMsg("成功!");
        result.setData(data);
        return result;
    }
}
