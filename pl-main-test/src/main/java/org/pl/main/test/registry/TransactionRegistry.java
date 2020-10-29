package org.pl.main.test.registry;

import org.pl.main.annotation.MainRegister;
import org.pl.main.push.impl.DefPusher;
import org.pl.main.registry.Registry;

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
