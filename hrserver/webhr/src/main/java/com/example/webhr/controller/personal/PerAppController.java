package com.example.webhr.controller.personal;

import com.example.webhr.model.*;
import com.example.webhr.service.AppraiseService;
import com.example.webhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工考评
 */
@RestController
@RequestMapping("/personnel/appraise")
public class PerAppController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    AppraiseService appraiseService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithApp(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10")Integer size,String keywords){
        return employeeService.getEmployeeByPageWithApp(page,size,keywords);
    }

    @GetMapping("/empnames")
    public List<Employee> getEmpnames(){
        return employeeService.getEmpnames();
    }

    @DeleteMapping("/{id}")
    public RespBean deleteApp(@PathVariable Integer id){
        if(appraiseService.deleteApp(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败!");
    }

    @PostMapping("/")
    public RespBean addApp(@RequestBody Appraise appraise){
        if(appraiseService.addApp(appraise) == 1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败!");
    }

    @PutMapping("/")
    public RespBean updateApp(@RequestBody Appraise appraise){
        if(appraiseService.updateApp(appraise)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
