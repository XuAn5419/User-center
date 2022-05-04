package com.xiaoliu.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoliu.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-03-15 11:06:37
*/
public interface UserService extends IService<User> {
    //用户登录状态,相当于一个KEY,可以查找到唯一的数值


    long userRegister(String userAccount, String userPassword, String checkPassword);

    //往请求的session里设置值，读取值
    public User doLogin(String userAccount, String userPassword,HttpServletRequest request);

    User getSafetyUser(User originUser);

    int userLogout(HttpServletRequest request);
}
