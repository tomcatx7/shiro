package com.example.cms.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SessionRedisDao extends AbstractSessionDAO
{
    private static final Logger logger = LogManager
            .getLogger( SessionRedisDao.class );

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private int expire = 3600000;

    private String keyPrefix = "shrio-key:";

    //    @Override
    //    protected Serializable doCreate(Session session) {
    //        //将首次生成的session存入redis，并返回sessionid
    //        Serializable sessionID = super.doCreate(session);
    //        redisTemplate.opsForValue().set(sessionID.toString(),session);
    //        return sessionID;
    //    }
    //
    //    @Override
    //    protected Session doReadSession(Serializable sessionId) {
    //        //根据产生的sessionID 从缓存中读取session
    //        Session session = super.doReadSession(sessionId);
    //        if (session == null){
    //            session = (Session) redisTemplate.opsForValue().get(sessionId.toString());
    //        }
    //        return session;
    //    }
    //
    //    @Override
    //    protected void doUpdate(Session session) {
    //        //更新缓存中的session
    //        super.doUpdate(session);
    //        redisTemplate.opsForValue().set(session.getId().toString(),session);
    //    }
    //
    //    @Override
    //    protected void doDelete(Session session) {
    //        //删除缓存中的session
    //        super.doDelete(session);
    //        redisTemplate.opsForValue().getOperations().delete(session.getId().toString());
    //    }

    @Override
    public void update( Session session ) throws UnknownSessionException
    {
        this.saveSession( session );
    }

    private void saveSession( Session session ) throws UnknownSessionException
    {
        if( session == null || session.getId() == null )
        {
            logger.error( "session or session id is null" );
            return;
        }
        String key = session.getId().toString();
        session.setTimeout( expire );
        redisTemplate.opsForValue()
                .set( keyPrefix + key, session, expire, TimeUnit.MILLISECONDS );
    }

    @Override
    public void delete( Session session )
    {
        redisTemplate.delete( keyPrefix + session.getId().toString() );
    }

    @Override
    public Collection<Session> getActiveSessions()
    {
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = redisTemplate.keys( this.keyPrefix + "*" );
        if( keys != null && keys.size() > 0 )
        {
            for( String key : keys )
            {
                Session s = (Session) redisTemplate.opsForValue().get( key );
                sessions.add( s );
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate( Session session )
    {
        Serializable sessionId = this.generateSessionId( session );
        this.assignSessionId( session, sessionId );
        this.saveSession( session );
        logger.info( "create session ,sessionId :"+ sessionId.toString());
        return sessionId;

    }

    @Override
    protected Session doReadSession( Serializable sessionId )
    {
        if( sessionId == null )
        {
            logger.error( "session id is null" );
            return null;
        }
        Session s = (Session) redisTemplate.opsForValue()
                .get( keyPrefix + sessionId.toString() );
        return s;
    }
}
