package com.example.webhr.controller.statistics;

import com.example.webhr.model.RespBean;
import com.example.webhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/statistics/all")
public class StaAllController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/degreeCount")
    public RespBean getDegreeCount(){
        return employeeService.getDegreeCount();
    }

    @GetMapping("/genderCount")
    public RespBean getGenderCount(){
        return employeeService.getGenderCount();
    }
}
