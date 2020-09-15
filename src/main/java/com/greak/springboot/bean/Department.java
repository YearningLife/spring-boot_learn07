package com.greak.springboot.bean;

import java.io.Serializable;
/**
 *
 * @description: TODO 部门表,向redis存储数据时，需要序列化
 * @author: zero
 * @date: 2020/9/15
 * @version: 1.0
 */
public class Department implements Serializable {

	private Integer id;
	private String departmentName;

	public Department() {
	}
	
	public Department(int i, String string) {
		this.id = i;
		this.departmentName = string;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}
	
}
