package com.greak.springboot;

import com.greak.springboot.bean.Employee;

import com.greak.springboot.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootLearn07ApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;


    @Test
    void contextLoads() {
    }

    @Test
    void getEmpInfo(){
        Employee employee = employeeMapper.selectEmpById(1);
        System.out.println("测试类中打印"+employee.toString());
    }

}
