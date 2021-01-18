package com.doily.demo.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName User
 * @description:TODO
 * @author:lky
 * @date:2021/1/8 11:58
 */
@Entity
@Table(name = "user")
public class User {
    private String name;
    @Id
    @GeneratedValue(generator = "increment_generator")
    @GenericGenerator(name = "increment_generator", strategy = "increment")
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(){

    }

}
