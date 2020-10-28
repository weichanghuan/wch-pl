package org.pl.spring.config;

import org.pl.main.registry.Registry;
import org.pl.spring.registry.DefRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class RegistryConfig {

    // 默认自带的注册中心
    @Bean("defRegistry")
    public Registry springDefRegistry() {
        return new DefRegistry();
    }


}
