package com.example.cms.controller;

import com.example.cms.domain.ResponseBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class SsoController
{
    private static final Logger logger = LogManager.getLogger();

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @RequestMapping(value = "/sso/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseBase> doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("rememberMe") boolean rememberMe )
    {
        System.out.println("doLogin");
        System.out.println( "username:"+username );
        System.out.println( "password"+password );
        System.out.println( "remeberme:"+rememberMe );
        UsernamePasswordToken token = new UsernamePasswordToken( username,
                password, rememberMe );
        ResponseBase<String> responseBase = new ResponseBase<>();

        try
        {
            Subject subject = SecurityUtils.getSubject();
            System.out.println( subject.isAuthenticated() );
            subject.login( token );
            System.out.println( "subject auth?:" + subject.isAuthenticated() );
        }
        catch( UnknownAccountException e )
        {
            responseBase.setData( "/error" );
            responseBase.setCode( 1 );
            logger.error( "UnknownAccountException"+e );
            return new ResponseEntity<>( responseBase, HttpStatus.OK );
        }
        catch( IncorrectCredentialsException e )
        {
            responseBase.setData( "/error" );
            responseBase.setCode( 1 );
            logger.error( "UnknownAccountException"+e );
            return new ResponseEntity<>( responseBase, HttpStatus.OK );
        }
        responseBase.setData( "/index" );
        responseBase.setCode( 1 );
        return new ResponseEntity<>( responseBase, HttpStatus.OK );
    }

    @RequestMapping("sso/logout")
    @ResponseBody
    public String logout( HttpServletRequest request ){
        String keyPrefix = "shrio-key:";
        Cookie[] cookies = request.getCookies();
        for( Cookie cookie : cookies )
        {
            if( cookie.getName().equals( "JSESSIONID" ) ){
                String value = cookie.getValue();
                Boolean delete = redisTemplate.delete( keyPrefix + value );
                return delete ? "logout!":"error";
            }
        }
        return "error";
    }
}
