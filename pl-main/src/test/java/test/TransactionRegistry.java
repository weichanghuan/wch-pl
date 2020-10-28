package test;

import org.pl.main.registry.Registry;

import java.util.UUID;

public class TransactionRegistry extends Registry {

    @Override
    public String getListenerId() {
        return UUID.randomUUID().toString();
    }
}
