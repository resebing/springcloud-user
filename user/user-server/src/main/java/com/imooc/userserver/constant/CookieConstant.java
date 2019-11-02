package com.imooc.userserver.constant;

import lombok.Getter;

public interface CookieConstant {

    String TOKEN = "token";

    String OPENNID = "openid";
    /**
     * 过期时间单位(秒）
     */
    Integer EXPIRE = 7200;
}
