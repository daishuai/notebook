package com.daishuai.redis.config;

import com.daishuai.redis.handler.DemoRedisMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Description Redis 发布订阅配置
 * @ClassName RedisMessageListenerConfig.java
 * @Author admin
 * @CreateTime 2022年03月20日 14:03:00
 * @Version 1.0.0
 */
@Configuration
public class RedisMessageListenerConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, MessageListenerAdapter demoTopicListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(demoTopicListener, new ChannelTopic(RedisTopicConstant.DEMO_TOPIC));
        container.setRecoveryInterval(100);
        return container;
    }

    @Bean
    public MessageListenerAdapter demoTopicListener(DemoRedisMessageHandler redisMessageHandler) {
        return new MessageListenerAdapter(redisMessageHandler, "handleDemoTopic");
    }

    @Bean
    public DemoRedisMessageHandler redisMessageHandler() {
        return new DemoRedisMessageHandler();
    }
}
