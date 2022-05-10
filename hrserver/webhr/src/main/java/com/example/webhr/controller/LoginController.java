package com.example.webhr.controller;

import com.example.webhr.config.VerificationCode;
import com.example.webhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class LoginController {  // .loginPage("/login") 如果没有登录，则会跳转到/login
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请登录！");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage(); //图片验证码
        String text = code.getText();  //验证码文本
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream()); //以流的形式输出
    }
}
