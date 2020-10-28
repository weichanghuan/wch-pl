package org.pl.spring.config;

import org.pl.main.push.Pusher;
import org.pl.main.push.impl.DefPusher;
import org.pl.main.registry.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(2)
public class PusherConfig {

    @Autowired
    @Qualifier("defRegistry")
    private Registry registry;

    @Bean("defPusher")
    public Pusher springDefPusher() {
        Pusher defPusher = new DefPusher();
        defPusher.addRegistry(registry.getRegistryName(), registry);
        return defPusher;
    }
}
