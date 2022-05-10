package com.example.webhr.config;

import com.example.webhr.model.Menu;
import com.example.webhr.model.Role;
import com.example.webhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 *  根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();//用户请求的url
        List<Menu> menus = menuService.getAllMenusWithRole();
        for(Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for(int i = 0; i < roles.size(); i++){
                    str[i] =roles.get(i).getName();
                }
                return SecurityConfig.createList(str);//字符串数组存储url对应菜单具有的角色
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN"); //没有匹配上的,则赋值为ROLE_LOGIN

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
