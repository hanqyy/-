package com.example.webhr.config;

import com.example.webhr.model.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证码过滤器
 * 在SpringSecurity前执行
 */
@Component
public class VerifyCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if("POST".equals(req.getMethod()) && "/doLogin".equals(req.getServletPath())){
            //登录请求
            String code = req.getParameter("code");
            String verify_code = (String)req.getSession().getAttribute("verify_code");
            if(code == null || "".equals(code) || !verify_code.equalsIgnoreCase(code)){
                //验证码不正确
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码填写错误")));
                out.flush();
                out.close();
                return;
            }else{
                chain.doFilter(req,resp);
            }
        }else{
            chain.doFilter(req,resp); //接着继续验证
        }
    }
}
