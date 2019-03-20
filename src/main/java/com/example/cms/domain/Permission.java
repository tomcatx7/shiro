package com.example.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 权限也是一个资源集，是某一功能的集合
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long permissionId;
    private String name;//权限名（ps：用户管理）
    private long pid;//父级id
    private int type;//权限类型
    private long systemId;//从属系统（不同系统不同权限）
    private String decription;
    private String icon;
    private String uri;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getSystemId() {
        return systemId;
    }

    public void setSystemId(long systemId) {
        this.systemId = systemId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }


    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", type=" + type +
                ", systemId=" + systemId +
                ", decription='" + decription + '\'' +
                '}';
    }
}
