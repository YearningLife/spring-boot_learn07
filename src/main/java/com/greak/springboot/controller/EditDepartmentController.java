package com.greak.springboot.controller;

import com.greak.springboot.bean.Department;
import com.greak.springboot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @author: zero
 * @date: 2020/9/17 23:50
 * @version: 1.0
 */
@RestController
public class EditDepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/dep/{id}")
    public String selectDepById(@PathVariable("id") Integer id){
        Department department = departmentService.selectDepById(id);
        System.out.println("controller层面获取部门信息："+department.toString());
        return department.toString();

    }
}
