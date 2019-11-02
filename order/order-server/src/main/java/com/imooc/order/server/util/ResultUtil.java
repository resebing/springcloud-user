package com.imooc.order.server.util;

import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.vo.ResultVo;

public class ResultUtil {

    public static ResultVo success(Object data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功!");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo error(ResultEnum resultEnum) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(resultEnum.getMessage());
        return resultVo;
    }
}
