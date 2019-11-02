package com.imooc.order.server.util;

import java.util.Random;

/**
 * ID生成工具
 */
public class IdUtil {

    /**
     * 生成唯一的主键
     * 格式:时间戳+随机数
     *
     * @return
     */
    public static synchronized String getUniqueId() {
        return System.currentTimeMillis() + String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
