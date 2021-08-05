package com.bzerok.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class AppConfig {

    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.host}")
    private String host;

    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, Integer.parseInt(port));
    }

    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}
