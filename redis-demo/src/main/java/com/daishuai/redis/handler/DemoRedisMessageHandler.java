package com.daishuai.redis.handler;

import com.daishuai.redis.config.RedisTopicConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description Redis消息处理
 * @ClassName DemoRedisMessageListener.java
 * @Author admin
 * @CreateTime 2022年03月20日 14:00:00
 * @Version 1.0.0
 */
@Slf4j
public class DemoRedisMessageHandler {

    public void handleDemoTopic(String message) {
        log.info("收到Topic: {}, 的消息: {}", RedisTopicConstant.DEMO_TOPIC, message);
    }
}
