package com.dong.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.usercenter.common.ErrorCodeEnum;
import com.dong.usercenter.constant.UserConstant;
import com.dong.usercenter.enums.UserGenderEnum;
import com.dong.usercenter.enums.UserStatusEnum;
import com.dong.usercenter.exception.BusinessException;
import com.dong.usercenter.mapper.UserMapper;
import com.dong.usercenter.model.domain.User;
import com.dong.usercenter.request.user.UserUpdateRequest;
import com.dong.usercenter.service.UserService;
import com.dong.usercenter.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhao
 * @description 针对表【user(用户基础信息)】的数据库操作Service实现
 * @createDate 2024-08-27 23:25:40
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册
     *
     * @param userAccount   用户名
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    @Override
    public String userRegister(String userAccount, String userPassword, String checkPassword) {
        LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(User::getUsername, userAccount);
        User user = this.getOne(lambdaQuery);
        //1.判断用户是否被注册
        if (!Objects.isNull(user)) {
            throw new BusinessException(ErrorCodeEnum.USER_IS_EXITS.getCode(), ErrorCodeEnum.USER_IS_EXITS.getMessage(), "");
        }
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCodeEnum.USERNAME_PASSWORD_IS_NULL.getCode(), ErrorCodeEnum.USERNAME_PASSWORD_IS_NULL.getMessage(), "");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCodeEnum.CHECK_PASSWORD_NOT_CORRECT.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage(), "");
        }
        //2.密码加密
        String md5userPassword = MD5Util.getMD5(userPassword);
        //3.注册
        User insert = new User();
        insert.setId(UUID.randomUUID().toString(true));
        insert.setUsername(userAccount);
        insert.setUserAccount(userAccount);
        insert.setPassword(md5userPassword);
        insert.setUpdateTime(new Date());
        this.save(insert);
        //4.返回用户id
        return insert.getId();
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @return User脱敏后的用户信息
     */
    @Override
    public User userLogin(String username, String userPassword, String validateCode, HttpServletRequest request) {
        String kaptcha = (String) request.getSession().getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(validateCode)) {
            throw new BusinessException(ErrorCodeEnum.VALIDATE_CODE_NOT_EXITS.getCode(), ErrorCodeEnum.VALIDATE_CODE_NOT_EXITS.getMessage(), "");
        }
        if (!kaptcha.equalsIgnoreCase(validateCode)) {
            throw new BusinessException(ErrorCodeEnum.VALIDATE_CODE_NOT_CORRECT.getCode(), ErrorCodeEnum.VALIDATE_CODE_NOT_CORRECT.getMessage(), "");
        }
        QueryWrapper<User> nameWrapper = new QueryWrapper<>();
        nameWrapper.eq("username", username);
        User userExits = this.getOne(nameWrapper);
        if (Objects.isNull(userExits)) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_EXITS.getCode(), ErrorCodeEnum.USER_NOT_EXITS.getMessage(), "");
        }
        if (StringUtils.isAnyBlank(username, userPassword)) {
            //抛出自定义业务异常，该异常通过配置的全局异常处理器捕获并处理
            throw new BusinessException(ErrorCodeEnum.USERNAME_PASSWORD_IS_NULL.getCode(), ErrorCodeEnum.USERNAME_PASSWORD_IS_NULL.getMessage(), "");
        }
        String md5userPassword = MD5Util.getMD5(userPassword);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", md5userPassword);
        User user = this.getOne(wrapper);
        //用户名不存在，请先注册
        if (Objects.isNull(user)) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCodeEnum.USERNAME_PASSWORD_NOT_CORRECT.getCode(), ErrorCodeEnum.USERNAME_PASSWORD_NOT_CORRECT.getMessage(), "");
        }
        //密码不正确
        if (!user.getPassword().equals(md5userPassword)) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCodeEnum.USERNAME_PASSWORD_NOT_CORRECT.getCode(), ErrorCodeEnum.USERNAME_PASSWORD_NOT_CORRECT.getMessage(), "");
        }
        //返回指定字段信息，脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setGender(UserGenderEnum.MALE);
        safetyUser.setUserStatus(UserStatusEnum.ONLINE);
        safetyUser.setUserRole(user.getUserRole());
        //记录用户登录状态
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 查询用户列表
     *
     * @param username 用户名
     */
    @Override
    public List<User> getUserList(String username, String phone, String email, HttpServletRequest request) {
        //鉴权，只有管理员才能查看用户列表
//        if (!isAdmin(request)) {
//            return null;
//        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isBlank(username), "username", username)
                .like(!StringUtils.isBlank(phone), "phone", phone)
                .like(!StringUtils.isBlank(email), "email", email);
        return this.list(wrapper);
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
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
    public User updateUser(UserUpdateRequest param, HttpServletRequest request) {
        User updateUser = BeanUtil.toBean(param, User.class);
        updateUser.setUpdateTime(new Date());
        int row = this.getBaseMapper().updateById(updateUser);
        return updateUser;
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @Override
    public boolean deleteUsers(String id, HttpServletRequest request) {
        User loginInfo = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        //判空
        if (Objects.isNull(loginInfo)) {
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED.getCode(), ErrorCodeEnum.USER_LOGIN_EXPIRED.getMessage(), "");
        }
        //查看是否有删除权限,必须是管理员才可以删除用户
        if (UserConstant.ADMIN_ROLE != loginInfo.getUserRole()) {
            throw new BusinessException(ErrorCodeEnum.USER_NOT_ADMIN.getCode(), ErrorCodeEnum.USER_NOT_ADMIN.getMessage(), "");
        }
        return this.removeById(id);
    }

    /**
     * 身份校验
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User userInfo = (User) attribute;
        if (Objects.isNull(userInfo)) {
            return false;
        }
        return UserConstant.ADMIN_ROLE == userInfo.getUserRole();
    }
}




