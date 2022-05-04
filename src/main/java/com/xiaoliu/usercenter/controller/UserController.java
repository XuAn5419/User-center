package com.xiaoliu.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.usercenter.model.domain.User;
import com.xiaoliu.usercenter.model.domain.request.UserLoginRequest;
import com.xiaoliu.usercenter.model.domain.request.UserRegisterRequest;
import com.xiaoliu.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.xiaoliu.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.xiaoliu.usercenter.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("register")
    public Long userRegister(@RequestBody  UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest==null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)){
            return null;
        }
        long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return id;
    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request){
        Object userobj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser=(User) userobj;
        if(currentUser==null){
            return null;
        }
        long userId =currentUser.getId();
        //todo校验，用户是否合法
        User user = userService.getById(userId);

        return userService.getSafetyUser(user);

    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAllBlank(userAccount, userPassword)){
            return null;
        }

        return userService.doLogin(userAccount,userPassword,request);
    }

    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request){
        if (request==null){
            return null;
        }

        return userService.userLogout(request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest request){

        if (isNotAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }

        List<User> userList=userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @GetMapping("/delete")
    public boolean deleteUsers(@RequestBody Long id,HttpServletRequest request){
        if (isNotAdmin(request)) {
            return false;
        }

        return userService.removeById(id);
    }

    boolean isNotAdmin(HttpServletRequest request){
        //鉴权
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user =(User)userObj;
        if (user==null||user.getUserRole()==ADMIN_ROLE){
            return false;
        }
        return true;
    }

}
