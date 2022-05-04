package com.xiaoliu.usercenter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


//用户服务测试

@SpringBootTest
public class CarServiceTest {

    @Resource
    private CarService carService;

    @Test
    public void testAddCar(){


        /*long result = carService.carIncrease("川A123AB");
        Assertions.assertEquals(-1,result);
       result = carService.carIncrease("A123456");
        Assertions.assertEquals(-1,result);*/

        boolean result = carService.carChange("川A123AB", "南华大学", "奔驰", "https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/3641/9827db8d8c90bb33d711dbf02b2b42b2.jpg");

        Assertions.assertTrue(result);
    }

    @Test
    void userRegister(){

    }
}