package com.example.cms.test.bean;

public class Face implements Cloneable{
    private String name;

    public Face() {

    }

    public Face(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
