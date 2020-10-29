package org.pl.spring.config;

import org.pl.main.registry.Registry;
import org.pl.mq.api.Producer;
import org.pl.mq.rocket.impl.RocketMqProducer;
import org.pl.spring.registry.DefRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class RegistryConfig {

    @Autowired
    private RocketMQConfig rocketMQConfigAutowired;

    // 默认自带的注册中心
    @Bean("defRegistry")
    public Registry springDefRegistry() {
        DefRegistry defRegistry = new DefRegistry();
        // 默认注入rocketmq
        Producer producer=new RocketMqProducer(rocketMQConfigAutowired.getAddresses(),"RocketMqProducer");
        defRegistry.setProducer(producer);
        return defRegistry;
    }


}
