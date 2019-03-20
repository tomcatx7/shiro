package com.example.cms.domain;
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String u_name;
    private String realname;
    private String number;
    private String pictureUrl;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    private int orgnizetion;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOrgnizetion() {
        return orgnizetion;
    }

    public void setOrgnizetion(int orgnizetion) {
        this.orgnizetion = orgnizetion;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", u_name='" + u_name + '\'' +
                ", realname='" + realname + '\'' +
                ", number='" + number + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", orgnizetion=" + orgnizetion +
                '}';
    }
}
