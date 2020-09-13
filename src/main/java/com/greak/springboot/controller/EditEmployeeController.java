package com.greak.springboot.controller;

import com.greak.springboot.bean.Employee;
import com.greak.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * @description: TODO 编辑员工信息
 * @author: zero
 * @date: 2020/9/12 13:06
 * @version: 1.0
 */
@RestController
// @SuppressWarnings("unchecked")
public class EditEmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/emp/{id}")
    public Employee selectEmpById(@PathVariable("id") Integer id){
        // System.out.println("方法："+Thread.currentThread().getStackTrace()[1].getMethodName()+"  查询条件 ："+id);
        Employee employee = employeeService.selectDepById(id);
        // System.out.println("查询结果"+employee.toString());
        // System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+"查询出来的结果为"+employee.toString());
        return employee;
    }

    @GetMapping("/emp/all")
    public List<Employee> selectAllEmp(){
        // System.out.println("查询所有员工信息");
        List<Employee> employee = employeeService.selectAllEmp();
        System.out.println("查询所有员工信息" + employee.toString());
        return employee;

    }

    @GetMapping("/emp/update")
    public Employee updateEmp(Employee employee){
            employeeService.updateEmp(employee);
        return employee;
    }

    @GetMapping("/emp/del/{id}")
    public String delEmp(@PathVariable("id") Integer id, Model model){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/ins")
    public Employee insertEmp(Employee employee){
        employeeService.insertEmp(employee);
        return employee;
    }
}
