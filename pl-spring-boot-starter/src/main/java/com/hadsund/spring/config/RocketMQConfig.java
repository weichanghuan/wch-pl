package com.hadsund.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Value("${hkposs.rocketmq.username:}")
    private String username;

    @Value("${hkposs.rocketmq.password:}")
    private String password;

    @Value("${hkposs.rocketmq.name-server:localhost:9876}")
    private String addresses;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
