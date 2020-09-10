package com.greak.springboot.service;

import com.greak.springboot.bean.Employee;
import com.greak.springboot.bean.User;
import com.greak.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: zero
 * @date: 2020/9/10 23:02
 * @version: 1.0
 */

public class EmployeeService {


    EmployeeMapper employeeMapper;


    public Employee selectDepById(Integer integer){
        Employee employee = employeeMapper.selectEmpById(integer);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+"查询出来的结构为"+employee.toString());
        return employee;
    }
}
