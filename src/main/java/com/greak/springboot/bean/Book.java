package com.greak.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: TODO
 * @author: zero
 * @date: 2020/9/20 19:25
 * @version: 1.0
 */

public class Book {
    private String bookName;
    private String bookAuthor;

    public Book() {
    }

    public Book(String bookName, String bookAuthor) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
