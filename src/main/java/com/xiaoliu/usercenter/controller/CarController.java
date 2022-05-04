package com.xiaoliu.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.usercenter.model.domain.Car;
import com.xiaoliu.usercenter.model.domain.request.CarIncreaseRequest;
import com.xiaoliu.usercenter.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car")
public class CarController {
    //租借（修改状态）
    //展示所有车辆信息

    @Resource
    private CarService carService;

    @Autowired
    private UserController userController;

    @GetMapping("/search")
    public List<Car> searchUsers(String carNum, HttpServletRequest request) {

        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(carNum)) {
            queryWrapper.like("carNum", carNum);
        }

        List<Car> carList=carService.list(queryWrapper);
        return carList.stream().collect(Collectors.toList());
    }

    @PostMapping("increase")
    public Long carIncrease(@RequestBody CarIncreaseRequest carIncreaseRequest){
        if (carIncreaseRequest==null){
            return null;
        }
        String carNum = carIncreaseRequest.getCarNum();
        String carLocation = carIncreaseRequest.getCarLocation();
        String carModel = carIncreaseRequest.getCarModel();
        String carPhoto = carIncreaseRequest.getCarPhoto();

        if (StringUtils.isAllBlank(carNum)){
            return null;
        }
        long id = carService.carIncrease(carNum,carLocation,carModel,carPhoto);
        return id;
    }

    @PostMapping("/delete")
    public boolean deleteCars(@RequestBody Long carId,HttpServletRequest request){
        carService.removeById(carId);
        return true;
    }

    @GetMapping("/change")
    public boolean changeCars(@RequestBody Long carId,CarIncreaseRequest carIncreaseRequest){

        String carNum = carIncreaseRequest.getCarNum();
        String carLocation = carIncreaseRequest.getCarLocation();
        String carModel = carIncreaseRequest.getCarModel();
        String carPhoto = carIncreaseRequest.getCarPhoto();

        carService.carChange(carNum,carLocation,carModel,carPhoto);

        return carService.removeById(carId);
    }

    @GetMapping("/current")
    public List<Car> getCurrentUser(CarIncreaseRequest carIncreaseRequest){

        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("carNum");

        List<Car> carNumList=carService.list(queryWrapper);
        System.out.println(carNumList);
        return carNumList.stream().collect(Collectors.toList());

    }

    //功能，新增。删除，修改

    //订单模块

}
