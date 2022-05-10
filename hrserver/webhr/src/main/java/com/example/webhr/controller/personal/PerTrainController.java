package com.example.webhr.controller.personal;


import com.example.webhr.model.Employee;
import com.example.webhr.model.Employeetrain;
import com.example.webhr.model.RespBean;
import com.example.webhr.model.RespPageBean;
import com.example.webhr.service.EmployeeService;
import com.example.webhr.service.EmployeeTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工培训
 */
@RestController
@RequestMapping("/personnel/train")
public class PerTrainController {
    @Autowired
    EmployeeTrainService employeeTrainService;
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithTrain(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size,String keywords){
        return employeeService.getEmployeeByPageWithTrain(page,size, keywords);
    }

    @GetMapping("/empnames")
    public List<Employee> getEmpnames(){
        return employeeService.getEmpnames();
    }

    @PostMapping("/")
    public RespBean addTrain(@RequestBody Employeetrain employeetrain){
        if(employeeTrainService.addTrain(employeetrain) == 1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteTrain(@PathVariable Integer id){
        if(employeeTrainService.deleteTrain(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败!");
    }

    @PutMapping("/")
    public RespBean updateTrain(@RequestBody Employeetrain employeetrain){
        if(employeeTrainService.updateTrain(employeetrain)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
