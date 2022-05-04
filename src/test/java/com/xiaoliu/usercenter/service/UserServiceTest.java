package com.xiaoliu.usercenter.service;
import java.util.Date;

import com.xiaoliu.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


//用户服务测试

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){

        int[] a={17,56,21,7,81,54,22,10,4,3,2,1};
        int temp;
        //外层循环控制趟数
        //内层循环控制每趟排序多少次
        for (int i=0;i<a.length-1;i++)
            for (int j=0;j<a.length-1;j++){
                if(a[j]<a[j+1]){
                temp=a[j];
                a[j]=a[j+1];
                a[j+1]=temp;
            }
            }
        for (int aa: a) {
            System.out.println(aa);
        }

        Long result = userService.userRegister("xiaoLiu","123","123");

    }

    @Test
    void userRegister(){
        String userAccount="xiaoliunuli";
        String userPassword="123456789";
        String checkPassword="123456789";
       Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);
    }
}