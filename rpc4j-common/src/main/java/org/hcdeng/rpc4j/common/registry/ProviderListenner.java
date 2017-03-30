package org.hcdeng.rpc4j.common.registry;

/**
 * Created by hcdeng on 2017/3/30.
 */
public interface ProviderListenner {
    void onProviderChange(String serviceName);
}
