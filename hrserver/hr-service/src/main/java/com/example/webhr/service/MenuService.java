package com.example.webhr.service;

import com.example.webhr.mapper.MenuMapper;
import com.example.webhr.mapper.MenuRoleMapper;
import com.example.webhr.mapper.RoleMapper;
import com.example.webhr.model.Hr;
import com.example.webhr.model.Menu;
import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public List<Menu> getMenusByHrId() {
        Integer HrId = ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>)valueOperations.get("menu_"+HrId);
        if(CollectionUtils.isEmpty(menus)){
            //如果menus为空，则从数据库中取
           menus = menuMapper.getMenusByHrId(HrId);
           //把从数据库中获取的menus放到redis
            valueOperations.set("menu_"+HrId,menus);
        }
        return menus;
    }

    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result==mids.length;
    }
}
