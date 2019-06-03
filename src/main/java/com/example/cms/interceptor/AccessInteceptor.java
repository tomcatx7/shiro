package com.example.cms.interceptor;

import com.example.cms.annotation.AccessLimite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;


public class AccessInteceptor extends HandlerInterceptorAdapter {
    @Autowired
    public StringRedisTemplate stringredisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AccessLimite accessLimite = handlerMethod.getMethodAnnotation(AccessLimite.class);
        if (accessLimite == null){
            return true;
        }
        int maxCounts = accessLimite.maxCounts();
        int secends = accessLimite.secends();

        String key = request.getRequestURL().toString();
        String uid = request.getParameter("uid");
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();

        Integer nums = ops.get(key+uid);
        if (nums == null){
            ops.set(key+uid,maxCounts,secends);
        }else if (nums >0){
            ops.decrement(key+uid);
        }else {
            System.out.println("被限制流量:"+nums);
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
