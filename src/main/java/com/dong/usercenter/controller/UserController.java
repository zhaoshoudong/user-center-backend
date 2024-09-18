package com.dong.usercenter.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dong.usercenter.common.BaseController;
import com.dong.usercenter.common.BaseResponse;
import com.dong.usercenter.common.ResultUtils;
import com.dong.usercenter.common.TableDataInfo;
import com.dong.usercenter.constant.UserConstant;
import com.dong.usercenter.model.domain.User;
import com.dong.usercenter.request.user.*;
import com.dong.usercenter.service.UserService;
import com.dong.usercenter.util.ServletUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * @BelongsPackage: com.dong.usercenter.controller
 * @Author: shouDong.zhao
 * @CreateTime: 2024/8/29
 * @Description: 用户服务
 */
@Controller
public class UserController extends BaseController {

    @Value("${shiro.rememberMe.enabled}")
    private boolean rememberMe;

    @Resource
    private UserService userService;

    private final Path videoDir = Paths.get("D:\\testVideo");

    //用户注册
    @PostMapping("/user/register")
    @ResponseBody
    public BaseResponse<String> userRegister(@RequestBody @Validated UserRegisterRequest param) {
        String userId = userService.userRegister(param.getUserAccount(), param.getUserPassword(), param.getCheckPassword());
        return ResultUtils.success(userId);
    }

    /**
     * 用户登录
     */
    @PostMapping("/user/login")
    @ResponseBody
    public BaseResponse<User> userLogin(@RequestBody @Validated UserLoginRequest param, HttpServletRequest request, Model model) {
        User user = userService.userLogin(param.getUserAccount(), param.getUserPassword(), request);
        model.addAttribute("user", user);
        return ResultUtils.success(user);
    }

    /**
     * 用户修改
     */
    @PostMapping("/user/update")
    @ResponseBody
    public BaseResponse<User> updateUser(@RequestBody @Validated UserUpdateRequest param, HttpServletRequest request) {
        User user = userService.updateUser(param, request);
        return ResultUtils.success(user);
    }

    /**
     * 查询用户列表
     */
    @GetMapping(value = "/user/toList")
    public String userToList() {
        return "table";
    }

    /**
     * 查询用户列表
     */
    @PostMapping(value = "/user/list")
    @ResponseBody
    public TableDataInfo userList(UserListRequest param, HttpServletRequest request) {
        AtomicInteger start = new AtomicInteger();
        startPage();
        List<User> userList = userService.getUserList(param.getUsername(), param.getPhone(), param.getEmail(), request).stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return getDataTable(userList);
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<String> delete(UserDeleteRequest param, HttpServletRequest request) {
        boolean flag = userService.deleteUsers(param.getId(), request);
        if (!flag) {
            return ResultUtils.error("删除失败!");
        }
        return ResultUtils.success("删除成功");
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, ModelMap mmap) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        // 是否开启记住我
        mmap.put("isRemembered", rememberMe);
        // 是否开启用户注册
        mmap.put("isAllowRegister", true);
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String add() {
        return "add";
    }

    @RequestMapping(value = "/videos/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<org.springframework.core.io.Resource> getVideo(@PathVariable String filename) throws MalformedURLException {
        Path videoPath = videoDir.resolve(filename).normalize();
        System.out.println(videoPath.toUri());
        org.springframework.core.io.Resource resource = new UrlResource(videoPath.toUri());
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Video not found " + filename);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return "index";
    }

    @RequestMapping(value = "/test", produces = "application/json", method = RequestMethod.POST)
    public String testController(@RequestParam("jsonObject") String jsonObject) {
        JSONObject objectParam = JSON.parseObject(jsonObject);
        String name = objectParam.getString("name");
        String age = objectParam.getString("age");
        System.out.println("name:" + name + "\t" + "age:" + age);
        return JSON.toJSONString(objectParam.toString());
    }
}
