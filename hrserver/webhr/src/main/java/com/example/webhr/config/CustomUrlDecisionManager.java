package com.example.webhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    //decide方法的第一个参数，Authentication authentication保存了当前登录用户的角色信息
    // 第三个参数是 Collection<ConfigAttribute> configAttributes，是从CustomFilterInvocationSecurityMetadataSource.java的getAttributes方法传来
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();//请求的url所需的角色creatList
            if("ROLE_LOGIN".equals(needRole)){
                if ((authentication instanceof AnonymousAuthenticationToken)) {//匿名用户
                    throw new AccessDeniedException("尚未登录，请登录！");
                }else {
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//获取当前用户登录的角色
            //判断用户的角色是否有所需要的角色
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(needRole)){ //用户具备所需的角色
                    return;
                }
            }
        }
       throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
