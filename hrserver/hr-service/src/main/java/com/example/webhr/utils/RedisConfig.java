package com.example.webhr.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //String 类型key序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //String类型value序列化器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型key序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型value序列化器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;

    }
}
