package com.daishuai.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description RedisDemo启动类
 * @className RedisApplication.java
 * @author admin
 * @createTime 2022年03月20日 13:50:00
 * @version 1.0.0
 */
@EnableScheduling
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
