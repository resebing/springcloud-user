package com.imooc.userserver.service;

import com.imooc.userserver.dataObject.UserInfo;

public interface UserInfoService {

    UserInfo findByOpenid(String openid);

}
