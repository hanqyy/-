package com.example.webhr.service;

import com.example.webhr.mapper.SalaryMapper;
import com.example.webhr.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    public int addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public int deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public int updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
