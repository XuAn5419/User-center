package com.xiaoliu.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoliu.usercenter.model.domain.User;
import com.xiaoliu.usercenter.service.UserService;
import com.xiaoliu.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiaoliu.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-03-15 11:06:37
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

        @Resource
        private UserMapper userMapper;

        private static final String SALT="xiaoliu";



        @Override
        public long userRegister(String userAccount,
                                 String userPassword, String checkPassword)
        {
            if (StringUtils.isAllBlank(userAccount,userPassword,checkPassword)) {
                return -1;
            }
            if (userAccount.length()<4) {
                return -1;
            }
            if (userPassword.length()<8||checkPassword.length()<8){
                return -1;
            }
            //账号不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount",userAccount);
            long count= userMapper.selectCount(queryWrapper);
            if(count>0){
                return -1;
            }

            //识别字符
            //账户不能包含特殊字符
            String validPattern ="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
            if (matcher. find()){
            return -1;
            }

            //密码不能相同
            if(!checkPassword.equals(userPassword)){
                return -1;
            }

            //加密
            String newPassword = DigestUtils.md5DigestAsHex((SALT+ userPassword).getBytes());

            //加入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(newPassword);
            boolean saveResult = this.save(user);
            if (!saveResult){
                return -1;
            }

            return user.getId();
        }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAllBlank(userAccount,userPassword)) {
            return null;
        }
        if (userAccount.length()<4) {
            return null;
        }
        if (userPassword.length()<8){
            return null;
        }
        //账号不能重复

        //识别字符
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if ( matcher. find()){
            return null;
        }

        //加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT+ userPassword).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",newPassword);
        User user=userMapper.selectOne(queryWrapper);
        if (user==null){
            log.info("user login failed, userAccount cannot match userPassword");
        return null;
        }

        User safetyUser =getSafetyUser(user);
        //得到session，修改值
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser){
            //校验
        if(originUser==null) {
            return null;
        }
        //用户脱敏
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);

        return 1;
    }
}
