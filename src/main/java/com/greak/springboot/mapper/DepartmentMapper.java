package com.greak.springboot.mapper;

import com.greak.springboot.bean.Department;
import org.apache.ibatis.annotations.*;

/**
 * @description: TODO department 映射文件
 * @author: zero
 * @date: 2020/9/10 22:20
 * @version: 1.0
 */
 @Mapper
public interface DepartmentMapper {

    @Select("SELECT * FROM department WHERE ID = #{id}")
    public Department selectDepById(Integer id) ;

    @Select("SELECT * FROM department")
    public Department selectAllDep();

    @Update("update department set departmentName = #{departmentName} where id = #{id}")
    public void updateDepById(Department department);

    @Insert("insert into department (departmentName) values(#{departmentName})")
    public void insertDep(Department department);

    @Delete("delete from department where id = #{id}")
    public void deleteById(Department department);
}
