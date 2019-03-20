package com.example.cms.controller;

import com.example.cms.domain.ResponseBase;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoController {

    @RequestMapping(value = "/sso/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseBase> doLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rememberMe") boolean rememberMe){
        System.out.println(username);
        System.out.println(password);
        System.out.println(rememberMe);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe);
        ResponseBase<String> responseBase = new ResponseBase<>();

        try {
            Subject subject = SecurityUtils.getSubject();
            System.out.println(subject.isAuthenticated());
            subject.login(token);
            System.out.println("subject auth?:" +subject.isAuthenticated());
        }catch (UnknownAccountException e) {
            responseBase.setData("/error");
            responseBase.setCode(1);
            return  new ResponseEntity<>(responseBase, HttpStatus.OK);
        } catch (IncorrectCredentialsException e) {
            responseBase.setData("/error");
            responseBase.setCode(1);
            return  new ResponseEntity<>(responseBase, HttpStatus.OK);
        }
        responseBase.setData("/index");
        responseBase.setCode(1);
        return new ResponseEntity<>(responseBase, HttpStatus.OK);
    }
}
