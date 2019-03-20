package com.example.cms.dao;

import com.example.cms.common.BaseDao;
import com.example.cms.domain.User;

public interface UserDao extends BaseDao<User> {
    public User findUserByName(String username);
}
