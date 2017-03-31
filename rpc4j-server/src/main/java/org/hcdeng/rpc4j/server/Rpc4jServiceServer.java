package org.hcdeng.rpc4j.server;

import com.google.common.base.Strings;
import org.hcdeng.rpc4j.common.config.ConfigManager;
import org.hcdeng.rpc4j.common.config.Constants;
import org.hcdeng.rpc4j.common.registry.RegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class Rpc4jServiceServer {

    private static Logger LOGGER = LoggerFactory.getLogger(Rpc4jServiceServer.class);

    private static Map<String, Object> serviceImplMap = new ConcurrentHashMap<>();

    private static volatile  boolean started = false;

    private static String host = ConfigManager.instance().getProperty(Constants.SERVER_HOST_CONFIG);

    private static int port ;
    static {
        String pStr = ConfigManager.instance().getProperty(Constants.SERVER_PORT_CONFIG);
        port = Strings.isNullOrEmpty(pStr) ? Constants.DEFAULT_SERVER_PORT : Integer.parseInt(pStr);
    }

    static {
        startUp();
    }

    private static void startUp(){
        if(!started){
            synchronized (Rpc4jServiceServer.class){
                if(!started){
                    try {
                        Rpc4jServer.startUp(port);
                        RegistryManager.startUp();
                    }catch (Exception e){
                        LOGGER.warn("fail to start the service server: "+e.getMessage());
                    }
                }
            }
        }
    }

    public static void addService(String serviceName, Object impl){
        serviceImplMap.putIfAbsent(serviceName, impl);
        RegistryManager.publishService(serviceName, host, port);
    }

    public static Object getActualImpl(String serviceName){
        return serviceImplMap.get(serviceName);
    }

}
