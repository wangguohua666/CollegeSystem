package com.zb.woshidren.dao;


import com.zb.woshidren.model.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {

    //根据员工编号查询员工信息
    Employee findByNumberForUpdate(@Param("number") Integer number);


    Integer findMaxEmpNumber();
    //插入员工信息
    void insertEmployee(Employee employee);


    //删除员工
    void deleteEmpByNum(Integer employeeNumber);

    Employee findByNum(@Param("employeeNumber") Integer employeeNumber);

    List<Employee> findAllEmp();
}
