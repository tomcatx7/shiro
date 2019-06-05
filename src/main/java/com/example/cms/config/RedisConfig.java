package com.example.cms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig
{
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate getRedisTemplate()
    {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate( redisTemplate, redisConnectionFactory );
        return redisTemplate;
    }

    private void initDomainRedisTemplate( RedisTemplate redisTemplate,
            RedisConnectionFactory factory )
    {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
                Object.class );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility( PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY );
        objectMapper
                .enableDefaultTyping( ObjectMapper.DefaultTyping.NON_FINAL );
        jackson2JsonRedisSerializer.setObjectMapper( objectMapper );

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( jackson2JsonRedisSerializer );
        redisTemplate.setHashValueSerializer( jackson2JsonRedisSerializer );
        redisTemplate.setHashKeySerializer( new StringRedisSerializer() );
        redisTemplate.setConnectionFactory( factory );
    }
}
