package com.imooc.userserver.controller;

import com.imooc.userserver.Util.CookieUtil;
import com.imooc.userserver.constant.CookieConstant;
import com.imooc.userserver.constant.RedisConstant;
import com.imooc.userserver.dataObject.UserInfo;
import com.imooc.userserver.enums.RoleEnum;
import com.imooc.userserver.service.UserInfoService;
import com.imooc.userserver.vo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 买家登陆
     * 1、比对openid
     * 2、判断角色
     * 3、将opendid=abc设置到cookie
     *
     * @param openid   openid
     * @param response 返回cookie信息
     */
    @RequestMapping(value = "/buyer")
    public ResultVo buyerLogin(@RequestParam("openid") String openid, HttpServletResponse response) {
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return new ResultVo(101, "登陆失败!");
        }
        // 买家
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
            return new ResultVo(101, "角色类型不符合!");
        }
        CookieUtil.setCookie(response, CookieConstant.OPENNID, openid, CookieConstant.EXPIRE);
        return new ResultVo(100, "买家登陆成功!");
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 卖家登陆
     * 1、比对openid
     * 2、判断角色
     * 3、设置Redis key=UUID value=xyz  因为有可能Redis抛出异常
     * 4、将opendid=xyz设置到cookie
     *
     * @param openid   openid
     * @param response 返回cookie信息
     */
    @RequestMapping(value = "/seller")
    public ResultVo sellerLogin(@RequestParam("openid") String openid, HttpServletRequest request, HttpServletResponse response) {
        // 判断是否已登陆
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie != null) {
            String token = cookie.getValue();
            String value = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN, token));
            if (StringUtils.isNotBlank(value)) {
                return new ResultVo(100, "卖家登陆成功!");
            }
        }
        // 1、比对openid
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return new ResultVo(101, "登陆失败!");
        }
        // 2、判断角色
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())) {
            return new ResultVo(101, "角色类型不符合!");
        }
        // 3、设置Redis key=UUID value=xyz  因为有可能Redis抛出异常
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.EXPIRE;
        String redisKey = String.format(RedisConstant.TOKEN, token);
        // 设置key = token_UUID,value=openid,过期时间7200/s
        stringRedisTemplate.opsForValue().set(redisKey, openid, expire, TimeUnit.SECONDS);
        // 4、将opendid=xyz设置到cookie
        CookieUtil.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
        return new ResultVo(100, "卖家登陆成功!");
    }
}
