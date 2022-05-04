package com.xiaoliu.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号 
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性 别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态0-正常
     */
    private Integer userStatus;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 足否删除
     */
    @TableLogic
    private Integer isDeLete;

    /**
     * 用户角色 0 普通用户
1管理员
2vip
     */
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}