package com.example.cms.controller;

import com.example.cms.annotation.AccessLimite;
import com.example.cms.domain.Permission;
import com.example.cms.domain.User;
import com.example.cms.service.PermissionService;
import com.example.cms.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PageController
{

    @Autowired
    public UserService userService;

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    @Autowired
    public PermissionService permissionService;

    @GetMapping("/test")
    @ResponseBody
    public int test()
    {
        return 1;
    }

    @GetMapping("/index")
    public ModelAndView page( HttpServletRequest request,
            HttpServletResponse response )
    {
        System.out.println( "index 本地端口:" + request.getLocalPort() );
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        User upmsUser = userService.findUserByName( username );
        ModelAndView mv = new ModelAndView( "manage/index" );
        mv.addObject( "upmsUser", upmsUser );
        List<Permission> upmsPermissions = permissionService.getAllPermission();
        mv.addObject( "upmsPermissions", upmsPermissions );
        return mv;
    }

    @GetMapping("/login")
    public String login( HttpServletRequest request,
            HttpServletResponse response )
    {
        System.out.println( "login 本地端口:" + request.getLocalPort() );
        return "sso/login";
    }

    @GetMapping("/user")
    public String user()
    {
        return "manage/user/index";
    }

    @GetMapping("/error")
    public String error()
    {
        return "error";
    }

    @GetMapping("/redisOp/{op}/{value}")
    @ResponseBody
    @AccessLimite(secends = 10, maxCounts = 5)
    public String redisOp( @PathVariable("op") String op,
            @PathVariable("value") String value )
    {
        ValueOperations<String, String> valueOperations = redisTemplate
                .opsForValue();
        switch( op )
        {
            case "add":
                valueOperations.set( "test", value );
                return "ok";
            case "get":
                String test = valueOperations.get( "test" );
                return test;
            default:
                return "unknown";
        }
    }
}
