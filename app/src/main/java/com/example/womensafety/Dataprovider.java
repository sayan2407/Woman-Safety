package com.example.womensafety;

public class Dataprovider {
    String id,name,phone;

    public Dataprovider(String id,String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

