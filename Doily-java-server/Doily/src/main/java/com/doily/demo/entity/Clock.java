package com.doily.demo.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @ClassName Clock
 * @description:TODO
 * @author:lky
 * @date:2021/4/27 22:26
 */
@Entity
@Table(name = "clock")
public class Clock {
    private String user;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Id
    @GeneratedValue(generator = "increment_generator")
    @GenericGenerator(name = "increment_generator", strategy = "increment")
    private Integer id;
    private Date date;
    private String time;
    private int type;
}
