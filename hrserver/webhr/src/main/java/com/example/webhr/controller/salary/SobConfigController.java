package com.example.webhr.controller.salary;

import com.example.webhr.model.Employee;
import com.example.webhr.model.RespBean;
import com.example.webhr.model.RespPageBean;
import com.example.webhr.model.Salary;
import com.example.webhr.service.EmployeeService;
import com.example.webhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工账套设置
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalary(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid,Integer sid){
        int result = employeeService.updateEmployeeSalaryById(eid,sid);
        if(result == 1 || result == 2){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
