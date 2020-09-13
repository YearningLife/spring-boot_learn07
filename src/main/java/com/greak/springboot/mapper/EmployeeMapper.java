package com.greak.springboot.mapper;

import com.greak.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.sql.Date;
import java.util.List;

/**
 * @description: TODO EmployeeMapper映射文件
 * @author: zero
 * @date: 2020/9/10 22:37
 * @version: 1.0
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    @Results(id = "empMap", value = {
            @Result(property = "id", column = "id", javaType = Integer.class,jdbcType = JdbcType.INTEGER,id = true),
            @Result(property = "lastName", column = "lastName",javaType = String.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "email", column = "email",javaType = String.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "gender", column = "gender",javaType = Integer.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "department", column = "department",javaType = String.class,jdbcType = JdbcType.VARCHAR),
            @Result(property = "birth", column = "birth",javaType = Date.class,jdbcType = JdbcType.VARCHAR)
    })
    public Employee selectEmpById(Integer id);

    @Select("select id,lastName,email,gender,department,birth from employee")
    @ResultMap("empMap")
    public List<Employee> selectAll();

    @Update("update employee set lastName = #{lastName},email = #{email},gender=#{gender},birth =#{birth},department=#{department} where id = #{id}")
    public void updateEmpById(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public void deleteEmpById(Integer id);

    @Insert("insert into employee (lastName,email,gender,birth ,department) values ( #{lastName},#{email},#{gender},#{birth},#{department})")
    public void insertEmp(Employee employee);
}
