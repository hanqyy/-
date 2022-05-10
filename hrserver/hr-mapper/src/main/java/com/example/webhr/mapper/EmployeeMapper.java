package com.example.webhr.mapper;

import com.example.webhr.model.Employee;
import com.example.webhr.model.Employeetrain;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);


    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Integer maxworkID();


    int addEmps(List<Employee> list);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeByPageWithSalary(Integer page, Integer size);

    int updateEmployeeSalaryById(@Param("eid") Integer eid,@Param("sid") Integer sid);


    List<Employee> getEmployeeByPageWithTrain(Integer page, Integer size, String keywords);

    Long getTotalWithTrain(String keywords);

    List<Employee> getEmpnames();

    List<Employee> getEmployeeByPageWithApp(Integer page, Integer size, String keywords);

    Long getTotalWithApp(String keywords);


    List<Employee> getAllEmployee();

    Integer getPrimaryCount();

    Integer getJuniorCount();

    Integer getHigherCount();

    Integer getMasterCount();

    Integer getDoctorCount();

    Integer getSpecialtyCount();

    Integer getUndergraduateCount();

    Integer getOtherCount();

    Integer getMaleCount();

    Integer getFemaleCount();
}