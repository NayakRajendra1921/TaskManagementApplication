package com.task.task_submission_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.task_submission_service.dto.ProfileDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean("loginRedisTemplate")
    public RedisTemplate<String, ProfileDto> loginServiceRedisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, ProfileDto> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        ObjectMapper objectMapper=new ObjectMapper();
        Jackson2JsonRedisSerializer<ProfileDto> serializer=new Jackson2JsonRedisSerializer<>(objectMapper,ProfileDto.class);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
