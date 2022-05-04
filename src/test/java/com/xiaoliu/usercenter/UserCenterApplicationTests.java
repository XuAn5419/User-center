package com.xiaoliu.usercenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoliu.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {

    private Object userAccount;

    @Test
    void testDigest() throws NoSuchAlgorithmException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount","123");
        System.out.println(queryWrapper);

        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mysql").getBytes());
        System.out.println(newPassword);

    }

    @Test
    void contextLoads() {
    }

}
