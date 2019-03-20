package com.example.cms.service;

import com.example.cms.dao.PermissionDao;
import com.example.cms.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PermissionService {
    @Autowired
    public PermissionDao permissionDao;

    public List<Permission> getAllPermission() {
        List<Permission> permissions = permissionDao.selectAll();
        return permissions;
    }
}
