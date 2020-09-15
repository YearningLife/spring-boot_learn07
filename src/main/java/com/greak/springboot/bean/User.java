package com.greak.springboot.bean;

import java.io.Serializable;

/**
 * @description: TODO 创建个人信息表,向redis存储数据时，需要序列化
 * @author: zero
 * @date: 2020/9/10 22:15
 * @version: 1.0
 */
public class User implements Serializable {
    private Integer id;
    private String lastName;
    private String documentId;
    private String documentType;
    private Integer telphone;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", documentType='" + documentType + '\'' +
                ", telphone=" + telphone +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getTelphone() {
        return telphone;
    }

    public void setTelphone(Integer telphone) {
        this.telphone = telphone;
    }

    public User(Integer id, String lastName, String documentId, String documentType, Integer telphone) {
        this.id = id;
        this.lastName = lastName;
        this.documentId = documentId;
        this.documentType = documentType;
        this.telphone = telphone;
    }

    public User() {
    }
}
