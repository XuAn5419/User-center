package com.xiaoliu.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoliu.usercenter.model.domain.Car;


/**
* @author Administrator
* @description 针对表【car(车辆信息表)】的数据库操作Service
* @createDate 2022-04-08 14:41:39
*/
public interface CarService extends IService<Car> {


    long carIncrease(String carNum, String carLocation, String carModel, String carPhoto);

    boolean isCarnumberNO(String carnumber);

     boolean carChange(String carNum, String carLocation, String carModel, String carPhoto);


}
