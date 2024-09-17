package com.dong.usercenter.controller;

import com.dong.usercenter.constant.UserConstant;
import com.dong.usercenter.model.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class IndexController {

    // 系统首页
    @GetMapping("/index")
//    @ResponseBody
    public String index(HttpServletRequest request, ModelMap map) {
        // 取身份信息
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (Objects.isNull(user)) {
            return "redirect:/login";
        }
        map.put("user",user);
        // 根据用户id取出菜单
        return "index-sys";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        return "main";
    }


}
