package com.hadsund.spring.config;

import com.hadsund.mq.api.Producer;
import com.hadsund.mq.rocket.impl.RocketMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class ProducerConfig {

    @Autowired
    private RocketMQConfig rocketMQConfigAutowired;

    @Bean("rocketMqProducer")
    @ConditionalOnMissingBean(RocketMqProducer.class)
    public Producer rocketMqProducer() {
        // 默认注入rocketmq
        return new RocketMqProducer(rocketMQConfigAutowired.getAddresses(),"RocketMqProducer");
    }
}
