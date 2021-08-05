package com.bzerok.server;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisRunner implements ApplicationRunner {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
