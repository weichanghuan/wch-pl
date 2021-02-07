package com.hadsund.spring.config;

import com.hadsund.main.push.Pusher;
import com.hadsund.main.push.impl.DefPusher;
import com.hadsund.main.registry.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(3)
public class PusherConfig {

    @Autowired
    @Qualifier("defRegistry")
    private Registry registry;

    @Bean("defPusher")
    @ConditionalOnMissingBean(DefPusher.class)
    public Pusher springDefPusher() {
        Pusher defPusher = new DefPusher();
        defPusher.addRegistry(registry.getRegistryName(), registry);
        return defPusher;
    }
}
