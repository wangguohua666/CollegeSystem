package com.zb.woshidren.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class Employee {
    private Integer id;
    private Integer employeeNumber;
    private String name;
    private String gender;
    private Date birthday;
    private String telephone;
    private String email;
    private String address;
    private String photo;
    private String education;
    private Integer departmentNumber;
    private Integer positionNumber;
    private Date inTime;
    private String password;
    private String notes;
    private String date;
}
