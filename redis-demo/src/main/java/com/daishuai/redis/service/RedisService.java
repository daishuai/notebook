package com.daishuai.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description TODO
 * @ClassName RedisService.java
 * @Author admin
 * @CreateTime 2022年03月21日 20:37:00
 * @Version 1.0.0
 */
@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Set<String> deletePattern(final String pattern) {
        Set<String> deleteKeys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().match("*" + pattern + "*").count(10).build();
            Cursor<byte[]> scan = connection.scan(scanOptions);
            Set<String> keys = new HashSet<>();
            while (scan.hasNext()) {
                String key = new String(scan.next());
                log.info("key: {}", key);
                keys.add(key);
            }
            return keys;
        });
        if (CollectionUtils.isNotEmpty(deleteKeys)) {
            redisTemplate.delete(deleteKeys);
        }
        return deleteKeys;
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void schedule() {
        Set<String> demo = deletePattern("demo");
        log.info(String.valueOf(demo.size()));
    }
}
