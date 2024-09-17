package com.dong.usercenter;

import cn.hutool.core.lang.UUID;
import com.dong.usercenter.enums.UserGenderEnum;
import com.dong.usercenter.enums.UserStatusEnum;
import com.dong.usercenter.model.domain.User;
import com.dong.usercenter.service.UserService;
import com.dong.usercenter.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Resource
    private UserService userService;


    @Test
    void addUser() {
        System.out.println("add User...");
        User user = new User();
        user.setId(UUID.fastUUID().toString(true));
        user.setUsername("王五");
        user.setUserAccount("wangwu");
        user.setPassword("123");
        user.setAvatarUrl("");
        user.setPhone("18345969098");
        user.setEmail("2509434424@qq.com");
        user.setGender(UserGenderEnum.MALE);
        user.setUserStatus(UserStatusEnum.ONLINE);
        user.setUpdateTime(new Date());
        user.setDeleted(0);
        boolean result = userService.save(user);
        System.out.println(result);
    }

    @Test
    void selectUsers(){
        System.out.println("select users...");
        List<User> users = userService.list();
        users.forEach(System.out::println);
    }

    @Test
    void md5(){
        //e10adc3949ba59abbe56e057f20f883e
        //e10adc3949ba59abbe56e057f20f883e
        String md5 = MD5Util.getMD5("123456");
        System.out.println(md5);
    }

}
