package com.zb.woshidren.controller;


import com.zb.woshidren.model.Employee;
import com.zb.woshidren.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {


    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;



    @RequestMapping("add")
    public String addEmp(Employee employee) {
        employeeService.findByNumForUpdate(employee);
            return "redirect:/employee/listPage";
    }



}
