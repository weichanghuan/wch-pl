package com.hadsund.main.test.registry;

import com.hadsund.main.annotation.MainRegister;
import com.hadsund.main.push.impl.DefPusher;
import com.hadsund.main.registry.Registry;

import java.util.UUID;

@MainRegister(pusher = DefPusher.class)
public class TransactionRegistry extends Registry {

    @Override
    public String getListenerId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getRegistryName() {
        return "TransactionRegistry";
    }
}
