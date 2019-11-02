package com.imooc.userserver.vo;

import lombok.Data;

@Data
public class ResultVo<T> {


    private Integer code;

    private String msg;
    private T data;

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo() {
        
    }
}
