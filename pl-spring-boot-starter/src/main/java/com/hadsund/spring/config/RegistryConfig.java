package com.hadsund.spring.config;

import com.hadsund.main.registry.Registry;
import com.hadsund.mq.api.Producer;
import com.hadsund.spring.registry.DefRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Order(2)
public class RegistryConfig {

    @Autowired
    @Qualifier("rocketMqProducer")
    private Producer producer;

    @Autowired
    private RedisTemplate redisTemplate;

    // 默认自带的注册中心
    @Bean("defRegistry")
    @ConditionalOnMissingBean(DefRegistry.class)
    public Registry springDefRegistry() {
        DefRegistry defRegistry = new DefRegistry();
        defRegistry.setProducer(producer);
        defRegistry.setRedisTemplate(redisTemplate);
        return defRegistry;
    }


}
