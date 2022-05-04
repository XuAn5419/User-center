package com.xiaoliu.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/*
* 新增汽车请求体
* */
@Data
public class CarIncreaseRequest implements Serializable {

    private String carNum;
    /**
     * 车辆型号
     */
    private String carModel;
    /**
     * 车辆所在地
     */
    private String carLocation;

    /**
     * 车辆照片
     */
    private String carPhoto;
}

