package com.example.cms.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class SessionRedisDao extends EnterpriseCacheSessionDAO {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        //将首次生成的session存入redis，并返回sessionid
        Serializable sessionID = super.doCreate(session);
        redisTemplate.opsForValue().set(sessionID.toString(),session);
        return sessionID;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        //根据产生的sessionID 从缓存中读取session
        Session session = super.doReadSession(sessionId);
        if (session == null){
            session = (Session) redisTemplate.opsForValue().get(sessionId.toString());
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        //更新缓存中的session
        super.doUpdate(session);
        redisTemplate.opsForValue().set(session.getId().toString(),session);
    }

    @Override
    protected void doDelete(Session session) {
        //删除缓存中的session
        super.doDelete(session);
        redisTemplate.opsForValue().getOperations().delete(session.getId().toString());
    }
}
