package com.xiaoliu.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;
/*
* 用户注册请求体
* */
@Data
public class UserRegisterRequest implements Serializable {



    private String userAccount, userPassword, checkPassword;
}

