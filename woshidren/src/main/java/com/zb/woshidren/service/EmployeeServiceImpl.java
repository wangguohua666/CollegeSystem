package com.zb.woshidren.service;

import com.zb.woshidren.dao.EmployeeDao;
import com.zb.woshidren.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeDao employeeDao;

    /**
     * 用悲观锁的方式保证唯一注册
     * @param employee
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee findByNumForUpdate(Employee employee) {
        Employee employee1 = employeeDao.findByNumberForUpdate(employee.getEmployeeNumber());
        if (employee1 != null) {
            LOG.info("===[手机号已被注册]===");
            return employee;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strdate = employee.getDate();
            Date date1 = null;
            try {
                date1 = sdf.parse(strdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            employee.setBirthday(date1);
            employee.setInTime(new Date());
            employeeDao.insertEmployee(employee);
        }
        return employee;
    }


    /**
     * 唯一索引的方式保证唯一注册
     * @param employee
     * @return
     */
    @Override
    public Integer uniqueInsertEmp(Employee employee) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = employee.getDate();
        Date date1 = null;
        try {
            date1 = sdf.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employee.setBirthday(date1);
        employee.setInTime(new Date());
        try {
            employeeDao.insertEmployee(employee);
        }catch (Exception e){
            if (e.getMessage().indexOf("Duplicate entry")>=0){
                Employee emp = employeeDao.findByNum(employee.getEmployeeNumber());
                return emp.getEmployeeNumber();
            }else {
                LOG.error(e.getMessage(),e);
                throw new RuntimeException(e.getMessage());
            }
        }
        return employee.getEmployeeNumber();
    }
}

