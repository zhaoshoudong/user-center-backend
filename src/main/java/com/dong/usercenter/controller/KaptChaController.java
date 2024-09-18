package com.dong.usercenter.controller;

import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @BelongsPackage: com.dong.usercenter.controller
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/18
 */
@Controller
public class KaptChaController {

    private static final Logger log = LoggerFactory.getLogger(KaptChaController.class);

    @Resource
    private Producer kaptChaProducer;

    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void getKaptCha(HttpServletResponse response, HttpSession session){
        String text = kaptChaProducer.createText();
        BufferedImage image = kaptChaProducer.createImage(text);
        session.setAttribute("kaptcha", text);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
       try{
           OutputStream os = response.getOutputStream();
           ImageIO.write(image,"png",os);
       } catch (IOException e){
           log.error("响应验证码失败:"+e);
       }
    }
}
