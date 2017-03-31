package org.hcdeng.rpc4j;

import junit.framework.TestCase;
import org.hcdeng.rpc4j.common.entity.ServiceProvider;
import org.hcdeng.rpc4j.common.registry.RegistryManager;
import org.junit.Test;

import java.util.List;

/**
 * Created by hcdeng on 2017/3/31.
 */

public class TestRegistry extends TestCase {

    @Test
    public void testPublishService() throws Exception{
        RegistryManager.startUp();
        RegistryManager.publishService("org.hcdeng.rpc4j.demo.api.DemoService", "127.0.0.1", 4080);
        RegistryManager.publishService("org.hcdeng.rpc4j.demo.api.DemoService", "192.168.1.1", 4040);
        List<ServiceProvider> providerInfos = RegistryManager.loadProviders("org.hcdeng.rpc4j.demo.api.DemoService");
        System.out.println(providerInfos);
        assertTrue(providerInfos.size() == 2);
    }
}
