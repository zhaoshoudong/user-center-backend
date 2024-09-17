package com.dong.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.usercenter.common.BaseResponse;
import com.dong.usercenter.model.domain.User;
import com.dong.usercenter.request.user.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ASUS
 * @description 针对表【user(用户基础信息)】的数据库操作Service
 * @createDate 2024-08-27 23:25:40
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param username      用户名
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return String
     */
    String userRegister(String username, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param username     用户名
     * @param userPassword 密码
     * @param request      请求
     * @return User
     */
    User userLogin(String username, String userPassword, HttpServletRequest request);

    /**
     * 查询用户列表
     *
     * @param username 用户名
     * @param request  请求
     * @return List<User>
     */
    List<User> getUserList(String username, String phone, String email, HttpServletRequest request);

    boolean deleteUsers(String ids, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    User updateUser(UserUpdateRequest param, HttpServletRequest request);
}
