package com.greak.springboot.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greak.springboot.bean.Employee;
import com.greak.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: TODO 结合cache用于查询数据库的数据，并且返回至controller层
 *                      CacheConfig  用于定义全局的cache名称
 * @author: zero
 * @date: 2020/9/10 23:02
 * @version: 1.0
 */
@CacheConfig(/*cacheNames = {"depCache"}*/ cacheManager = "empCacheManage")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RedisTemplate redisTemplate;


    /*
     *
     * @description: TODO
     * @param: integer
     * @return: com.greak.springboot.bean.Employee
     * @author zero
     * @date: 2020/9/13 11:49
     * 参考链接：https://juejin.im/post/6844903966615011335
     * 注解：
     *  前提： 须在启动器上面添加：@EnableCaching
     *  1. @Cacheable  多用于查询，方法执行前执行
     *  2.  cacheNames/value  二选一属性，指的是缓存名称
     *  3.  key/keyGenerator  二选一，键值对中的键值
     *  4.  condition ，筛选条件，符合条件的才会进行缓存,不符合数据库的值须在数据库中查询
     */
    @Cacheable(cacheNames="depCache",condition = "#integer > 1" ,keyGenerator="oneKeyGenerator")
    public Employee selectDepById(Integer integer){
        Employee employee = employeeMapper.selectEmpById(integer);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+"查询出来的结果为"+employee.toString());
        return employee;
    }


    @Cacheable(cacheNames="depCache",keyGenerator="oneKeyGenerator")
    public List<Employee> selectAllEmp() {
        List<Employee> empList = employeeMapper.selectAll();
        Iterator<Employee> iterator = empList.iterator();
        while (iterator.hasNext()) {
            Employee next = iterator.next();
            System.out.println("查询所有员工列表信息："+next.toString());
        }

        return  empList;
    }

    /*
     *
     * @description: TODO 执行update时更新缓存中的数据
     *                @CacheEvict 属性多用于删除和更新的方法之中
     *                beforeInvocation:在调用该方法之前是否应进行执行；默认情况下，仅删除关联键下的值。
     *                allEntries:是否删除缓存内的所有条目。
     * @param: employee
     * @return: com.greak.springboot.bean.Employee
     * @author zero
     * @date: 2020/9/13 15:12
     */
    @CacheEvict(cacheNames = "depCache" ,key = "#employee.id",beforeInvocation = true)
    public void updateEmp(Employee employee){
        employeeMapper.updateEmpById(employee);
        System.out.println("更新employee表数据："+employee.toString());
        // return employee;
    }
    /*
     *
     * @description: TODO 通过ID删除员工，并且删除cache中的数据 和update方法一致
     *                beforeInvocation:在调用该方法之前是否应进行执行；默认情况下，仅删除关联键下的值。
     *                allEntries:是否删除缓存内的所有条目。
     * @param: id
     * @return: void
     * @author zero
     * @date: 2020/9/13 15:39
     */
    @CacheEvict(cacheNames = "depCache" ,beforeInvocation = true,allEntries = true)
    public void deleteEmp(Integer id){
        employeeMapper.deleteEmpById(id);
        System.out.println("通过ID删除员工"+id);
    }

    /*
     *
     * @description: TODO  service层调用缓存新增员工
     *                  @CachePut: 它总是导致方法被调用并将其结果存储在关联的缓存中
     * @param: employee
     * @return: void
     * @author zero
     * @date: 2020/9/13 15:56
     */
    @CachePut(value = "depCache",keyGenerator="oneKeyGenerator")
    public void insertEmp(Employee employee){

        employeeMapper.insertEmp(employee);
        System.out.println("新增员工:"+employee.getId());
    }


}
