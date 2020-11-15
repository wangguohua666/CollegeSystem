package com.zb.woshidren.service;

import com.zb.woshidren.model.Employee;

public interface EmployeeService {


    Employee findByNumForUpdate(Employee employee);


     Integer uniqueInsertEmp(Employee employee);
}
