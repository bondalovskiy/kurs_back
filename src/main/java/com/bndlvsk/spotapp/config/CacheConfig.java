package com.bndlvsk.spotapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.bndlvsk.spotapp.service.StatsService;
import com.bndlvsk.spotapp.task.CacheTask;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder());
        return cacheManager;
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> method.getName() + "_" + StringUtils.arrayToDelimitedString(params, "_");
    }

    @Bean
    public CacheTask cacheTask(CacheManager cacheManager, StatsService statsService) {
        return new CacheTask(cacheManager, statsService);
    }
}
