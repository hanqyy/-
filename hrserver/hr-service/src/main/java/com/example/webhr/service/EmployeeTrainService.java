package com.example.webhr.service;

import com.example.webhr.mapper.EmployeeMapper;
import com.example.webhr.mapper.EmployeetrainMapper;
import com.example.webhr.model.Employeetrain;
import com.example.webhr.model.RespBean;
import com.example.webhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;


    public int addTrain(Employeetrain employeetrain) {
        return employeetrainMapper.insertSelective(employeetrain);
    }

    public int deleteTrain(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }


    public int updateTrain(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }
}
