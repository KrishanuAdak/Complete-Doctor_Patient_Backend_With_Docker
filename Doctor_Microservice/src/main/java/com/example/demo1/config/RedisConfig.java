package com.example.demo1.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo1.dto.DoctorDetailsDTO_Redis;
@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<String, List<DoctorDetailsDTO_Redis>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<DoctorDetailsDTO_Redis>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Keys as plain strings
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // Values as JSON (readable in Redis CLI, works with any POJO)
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
    


