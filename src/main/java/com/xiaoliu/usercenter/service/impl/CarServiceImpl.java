package com.xiaoliu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoliu.usercenter.mapper.CarMapper;
import com.xiaoliu.usercenter.model.domain.Car;
import com.xiaoliu.usercenter.model.domain.User;
import com.xiaoliu.usercenter.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【car(车辆信息表)】的数据库操作Service实现
* @createDate 2022-04-08 14:41:39
*/
@Service
@Slf4j
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
implements CarService {

    @Resource
    private CarMapper carMapper;

    @Override
    public long carIncrease(String carNum, String carLocation, String carModel, String carPhoto) {
        if (StringUtils.isAllBlank(carNum)) {
            return -1;
        }
        //车牌号正则表达式
        /*if (!isCarnumberNO(carNum)){
            return -1;
        }*/
        //车牌号不能重复
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("carNum",carNum);
        long count= carMapper.selectCount(queryWrapper);
        if(count>0){
            return -1;
        }

        //加入数据
        Car car = new Car();
        car.setCarNum(carNum);
        car.setCarLocation(carLocation);
        car.setCarModel(carModel);
        car.setCarPhoto(carPhoto);
        boolean saveResult = this.save(car);
        if (!saveResult){
            return -1;
        }

        return 1;
    }

    @Override
    public boolean carChange(String carNum, String carLocation, String carModel, String carPhoto) {
        if (StringUtils.isAllBlank(carNum)) {
            return false;
        }

        Car car = new Car();
        car.setCarNum(carNum);
        car.setCarLocation(carLocation);
        car.setCarModel(carModel);
        car.setCarPhoto(carPhoto);
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("carNum",carNum);
        carMapper.update(car,queryWrapper);

        return true;
    }

    public boolean isCarnumberNO(String carnumber) {
   /*
   1.常规车牌号：仅允许以汉字开头，后面可录入六个字符，由大写英文字母和阿拉伯数字组成。如：粤B12345；
   2.武警车牌：允许前两位为大写英文字母，后面可录入五个或六个字符，由大写英文字母和阿拉伯数字组成，其中第三位可录汉字也可录大写英文字母及阿拉伯数字，第三位也可空，如：WJ警00081、WJ京1234J、WJ1234X。
   3.最后一个为汉字的车牌：允许以汉字开头，后面可录入六个字符，前五位字符，由大写英文字母和阿拉伯数字组成，而最后一个字符为汉字，汉字包括“挂”、“学”、“警”、“军”、“港”、“澳”。如：粤Z1234港。
   4.新军车牌：以两位为大写英文字母开头，后面以5位阿拉伯数字组成。如：BA12345。
       */
        String carnumRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        if (StringUtils.isEmpty(carnumber)) return false;
        boolean flag = carnumber.matches(carnumRegex);
        return flag;
    }

}
