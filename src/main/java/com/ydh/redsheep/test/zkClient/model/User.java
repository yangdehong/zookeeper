package com.ydh.redsheep.test.zkClient.model;

import java.io.Serializable;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
public class User implements Serializable{

    private Integer id;
    private String name;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
