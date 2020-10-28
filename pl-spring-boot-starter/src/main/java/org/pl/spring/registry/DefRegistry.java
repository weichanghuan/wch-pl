package org.pl.spring.registry;

import org.pl.main.registry.Registry;

import java.util.UUID;

public class DefRegistry extends Registry {
    public String getListenerId() {
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

    public String getRegistryName() {
        return "defRegistry";
    }
}
