package com.example.webhr.controller;

import com.example.webhr.model.Menu;
import com.example.webhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;
    @GetMapping("/menu") //通过当前登录用户的id获得该有的菜单
    public List<Menu> getMenusByHrId(){ //根据当前登录的用户Id获得页面的菜单
        return menuService.getMenusByHrId();
    }
}
