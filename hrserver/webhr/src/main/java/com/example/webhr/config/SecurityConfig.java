package com.example.webhr.config;


import com.example.webhr.model.Hr;
import com.example.webhr.model.RespBean;
import com.example.webhr.service.HrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Autowired
    VerifyCodeFilter verifyCodeFilter;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception { //配置不需要拦截的接口
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Bean
    LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                Hr hr = (Hr)authentication.getPrincipal();
                RespBean ok = RespBean.ok("登录成功！", hr);
                hr.setPassword(null);
                String s = new ObjectMapper().writeValueAsString(ok);
                out.write(s);
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                RespBean respBean = RespBean.error(exception.getMessage());
                if(exception instanceof LockedException){
                    respBean.setMsg("账户被锁定，请联系管理员！");
                }else if (exception instanceof CredentialsExpiredException) {
                    respBean.setMsg("密码过期，请联系管理员!");
                } else if (exception instanceof AccountExpiredException) {
                    respBean.setMsg("账户过期，请联系管理员!");
                } else if (exception instanceof DisabledException) {
                    respBean.setMsg("账户被禁用，请联系管理员!");
                } else if (exception instanceof BadCredentialsException) {
                    respBean.setMsg("用户名或者密码输入错误，请重新输入!");
                }
                String s = new ObjectMapper().writeValueAsString(respBean);
                out.write(s);
                out.flush();
                out.close();
            }
        });
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                //.anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功！")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                //没有认证时，在这里处理结果，不要重定向
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(401);
                        PrintWriter out = resp.getWriter();
                        RespBean respBean = RespBean.error("访问失败！");
                        if (authException instanceof InsufficientAuthenticationException) {
                            respBean.setMsg("请求失败，请联系管理员！");
                        }
                        String s = new ObjectMapper().writeValueAsString(respBean);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
        });//替换filter
        http.addFilterAfter(loginFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
