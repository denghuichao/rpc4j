package org.hcdeng.rpc4j.client.provider;

import org.apache.commons.collections.CollectionUtils;
import org.hcdeng.rpc4j.common.entity.ServiceProvider;
import org.hcdeng.rpc4j.common.registry.ProviderListennerManager;
import org.hcdeng.rpc4j.common.registry.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class ProviderManager {

    private static Logger LOGGER = LoggerFactory.getLogger(ProviderManager.class);

    private static Map<String, List<ServiceProvider>> providerMap = new ConcurrentHashMap<String, List<ServiceProvider>>();

    private static ProviderManager INSTANCE = new ProviderManager();

    private ProviderManager(){
        startUp();
    }

    public static ServiceProvider getProvider(String serviceName) {
        return INSTANCE.getProvider_inner(serviceName);
    }

    private ServiceProvider getProvider_inner(String serviceName) {
        List<ServiceProvider> list = providerMap.get(serviceName);
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("can not find service named " + serviceName + " in the registry!");
        }

        return list.get(new Random().nextInt(list.size()));
    }

    private void loadProvidersOfService(String serviceName) {
        providerMap.put(serviceName, RegistryManager.loadProviders(serviceName));
    }

    private void startUp() {
        try {
            ProviderListennerManager.instance().registerProviderListenner(
                    (serviceName) -> {
                        loadProvidersOfService(serviceName);
                    });
            RegistryManager.startUp();
            providerMap.putAll(RegistryManager.loadProviders());
        } catch (Exception e) {
            LOGGER.warn("fail to start the ProviderManager: " + e.getMessage());
        }
    }
}
