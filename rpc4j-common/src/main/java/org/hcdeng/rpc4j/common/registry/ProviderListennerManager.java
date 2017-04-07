package org.hcdeng.rpc4j.common.registry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcdeng on 2017/3/30.
 */
public final class ProviderListennerManager implements ProviderListenner{

    /**
     * listenners for provider node state change events
     */
    private List<ProviderListenner> listenners = new ArrayList<ProviderListenner>();

    private ProviderListennerManager(){}

    /**
     * single instance
     * @return single instance of ProviderListennerManager
     */
    public static ProviderListennerManager instance(){
        return ProviderListennerManagerHolder.MANAGER_INSTANCE;
    }

    public void registerProviderListenner(ProviderListenner listenner){
        listenners.add(listenner);
    }

    public void unregisterProviderLinstenner(ProviderListenner listenner){
        listenners.remove(listenner);
    }

    @Override
    public void onProviderChange(String serviceName) {
        for(ProviderListenner listenner : listenners)
            listenner.onProviderChange(serviceName);
    }

    private static class ProviderListennerManagerHolder{
        private static final ProviderListennerManager MANAGER_INSTANCE = new ProviderListennerManager();
    }
}
