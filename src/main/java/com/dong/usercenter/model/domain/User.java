package com.dong.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dong.usercenter.enums.UserGenderEnum;
import com.dong.usercenter.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable{
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别（0-男，1-女）
     */
    private UserGenderEnum gender;

    /**
     * 用户状态（0-启用，1-禁用）
     */
    private UserStatusEnum userStatus;

    /**
     * 权限（0-普通用户，1-管理员）
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMF+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMF+8")
    private Date updateTime;

    /**
     * 是否删除（0-否，1-是）
     */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}