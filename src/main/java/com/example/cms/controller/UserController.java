package com.example.cms.controller;

import com.example.cms.domain.ResponseBase;
import com.example.cms.domain.User;
import com.example.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping("user/{username}")
    public ResponseEntity<ResponseBase> getUserByName(@PathVariable("username") String username){
        User user = userService.findUserByName(username);
        ResponseBase<User> result = new ResponseBase<>();
        result.setData(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
