package org.pl.spring.config;

import org.pl.mq.api.Producer;
import org.pl.mq.rocket.impl.RocketMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class ProducerConfig {

    @Autowired
    private RocketMQConfig rocketMQConfigAutowired;

    @Bean("rocketMqProducer")
    public Producer rocketMqProducer() {
        // 默认注入rocketmq
        return new RocketMqProducer(rocketMQConfigAutowired.getAddresses(),"RocketMqProducer");
    }
}
