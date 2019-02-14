package com.ydh.redsheep.master.ckclient.subscribe;

public class MyServerData {

    private String address;
    private Integer id;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "MyServerData [address=" + address + ", id=" + id + ", name="
                + name + "]";
    }


}
