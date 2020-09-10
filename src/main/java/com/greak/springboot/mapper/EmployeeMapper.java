package com.greak.springboot.mapper;

import com.greak.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;

/**
 * @description: TODO EmployeeMapper映射文件
 * @author: zero
 * @date: 2020/9/10 22:37
 * @version: 1.0
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee selectEmpById(Integer id);

    @Select("select * from employee")
    public Employee selectAll();

    @Update("update employee set lastName = #{lastName},email = #{email},gender=#{gender},birth =#{birth} where id = #{id}")
    public void updateEmpById(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public void deleteEmpById(Employee employee);

    @Insert("insert into employee (lastName,email,gender,birth ) values ( #{lastName},#{email},#{gender},#{birth})")
    public void insertEmp(Employee employee);
}
