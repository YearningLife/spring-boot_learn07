package com.greak.springboot.mapper;

import com.greak.springboot.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @description: TODO UserMapper映射文件
 * @author: zero
 * @date: 2020/9/10 22:48
 * @version: 1.0
 */
public interface UserMapper {

    @Select("select * from user")
    public User selectById(Integer id);

    @Update("update user set lastName = #{lastName},documentId=#{documentId},document_type=#{documentType},telphone = #{telphone} where id = #{id]")
    public void updateUserById(Integer id);

    @Insert("insert into user (lastName,documentId,document_type,telphone) values( #{lastName},#{documentId},#{documentType},#{telphone})")
    public void insertUser(User user);

    @Delete("delete from user where ")
    public void deleteUserById(Integer id );
}
