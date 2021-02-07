package com.hadsund.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/17 9:21 AM
 */
@Configuration
public class CacheConfig<K, V> {

    @Bean
    @ConditionalOnMissingBean(RedisMessageListenerContainer.class)
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        // 创建redis连接
        redisTemplate.setConnectionFactory(factory);
        // 序列化方式
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key的序列化方式是string类型
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value的序列化方式是 json
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
