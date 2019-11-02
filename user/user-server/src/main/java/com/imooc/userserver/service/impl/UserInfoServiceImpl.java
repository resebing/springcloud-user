package com.imooc.userserver.service.impl;

import com.imooc.userserver.dataObject.UserInfo;
import com.imooc.userserver.repository.UserInfoDao;
import com.imooc.userserver.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoDao.findByOpenid(openid);
    }
}
