package com.example.cms.service;

import com.example.cms.dao.UserDao;
import com.example.cms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;

    public User findUserByName(String username){
        User user = userDao.findUserByName(username);
        return user;
    }

}
