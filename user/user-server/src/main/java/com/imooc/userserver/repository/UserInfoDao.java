package com.imooc.userserver.repository;

import com.imooc.userserver.dataObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, String> {


    UserInfo findByOpenid(String openid);
}
