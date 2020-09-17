package com.greak.springboot.service;


import com.greak.springboot.bean.Department;
import com.greak.springboot.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: zero
 * @date: 2020/9/17 23:34
 * @version: 1.0
 */
@CacheConfig(cacheManager = "depCacheManage")
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Cacheable(value="dep",key = "#id")
    public Department selectDepById(Integer id){
        System.out.println("可查询的部门id为："+id);
       return departmentMapper.selectDepById(id);
   }
}
