package com.ydh.redsheep.master.ckclient;

import java.io.Serializable;

public class MyCkClientData implements Serializable {

    private Long cid;
    private String name;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
