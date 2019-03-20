package com.example.cms.test.bean;

public class Person implements Cloneable {
    private String name;
    private Face face;

    public Person(String name, Face face) {
        this.name = name;
        this.face = face;
    }
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person clone = (Person)super.clone();
        Face cloneface = (Face)this.face.clone();
        clone.setFace(cloneface);
        return clone;
    }

    public static void main(String[] args) {
        Person p = new Person();
        Face face = new Face();
        face.setName("f1");
        p.setFace(face);
        try {
            Person p1 = (Person)p.clone();
            System.out.println(p);
            System.out.println(p1);
            System.out.println(p == p1);
            p1.getFace().setName("f2");
            System.out.println(p.getFace() == p1.getFace());
            System.out.println(face.getName());

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
