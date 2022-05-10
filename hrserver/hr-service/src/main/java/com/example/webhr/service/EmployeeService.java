package com.example.webhr.service;

import com.example.webhr.mapper.EmployeeMapper;
import com.example.webhr.model.Employee;
import com.example.webhr.model.RespBean;
import com.example.webhr.model.RespPageBean;
import com.example.webhr.model.vo.BarVO;
import com.example.webhr.model.vo.GenderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //日期转换
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

   public RespBean getDegreeCount(){
       BarVO barVO = new BarVO();
       barVO.setOther(employeeMapper.getOtherCount());
       barVO.setPrimary(employeeMapper.getPrimaryCount());
       barVO.setJunior(employeeMapper.getJuniorCount());
       barVO.setHigher(employeeMapper.getHigherCount());
       barVO.setMaster(employeeMapper.getMasterCount());
       barVO.setDoctor(employeeMapper.getDoctorCount());
       barVO.setSpecialty(employeeMapper.getSpecialtyCount());
       barVO.setUndergraduate(employeeMapper.getUndergraduateCount());
       if(barVO != null){
           return RespBean.ok("最高学历ok",barVO);
       }
       return RespBean.error("最高学历error");
   }

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }


    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer maxworkID() {
        return employeeMapper.maxworkID();
    }

    public int deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public int addEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month / 12)));
        int result = employeeMapper.insertSelective(employee);
        if(result == 1){
           Employee emp = employeeMapper.getEmployeeById(employee.getId());
           logger.info(emp.toString());
           rabbitTemplate.convertAndSend("hqy.mail.welcome",emp);
        }
        return result;
    }

    public int addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {
        if(page != null && size != null){
            page = (page-1)*size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page,size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null,null));
        return respPageBean;
    }

    public int updateEmployeeSalaryById(Integer eid, Integer sid) {
        return employeeMapper.updateEmployeeSalaryById(eid,sid);
    }

    public RespPageBean getEmployeeByPageWithTrain(Integer page, Integer size, String keywords) {
        if(page != null && size != null){
            page = (page-1)*size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithTrain(page,size, keywords);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotalWithTrain(keywords));
        return respPageBean;
    }

    public List<Employee> getEmpnames() {
        return employeeMapper.getEmpnames();
    }

    public RespPageBean getEmployeeByPageWithApp(Integer page, Integer size, String keywords) {
        if(page != null && size != null){
            page = (page-1)*size;
        }
        List<Employee> list = employeeMapper.getEmployeeByPageWithApp(page,size,keywords);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotalWithApp(keywords));
        return respPageBean;
    }

    public RespBean getGenderCount() {
        GenderVO genderVO = new GenderVO();
        genderVO.setMale(employeeMapper.getMaleCount());
        genderVO.setFemale(employeeMapper.getFemaleCount());
        if(genderVO != null){
            return RespBean.ok("查询性别比列成功！",genderVO);
        }
        return RespBean.error("查询性别比列失败！");
    }
}
