package com.xiaoliu.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆信息表
 * @TableName car
 */
@TableName(value ="car")
@Data
public class Car implements Serializable {
    /**
     * 编码
     */
    @TableId(type = IdType.AUTO)
    private Integer carId;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 上次使用时间
     */
    private Date useLastTime;

    /**
     * 车辆型号
     */
    private String carModel;

    /**
     * 车辆状态 0-正常租借  1-出借中 -1损坏或修理
     */
    private Integer carStatus;

    /**
     * 剩余油量 百分比制
     */
    private Integer remainOil;

    /**
     * 租借用户id
     */
    private Integer rentId;

    /**
     * 租借用户
     */
    private String rentName;

    /**
     * 车辆所在地
     */
    private String carLocation;

    /**
     * 车辆照片
     */
    private String carPhoto;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}