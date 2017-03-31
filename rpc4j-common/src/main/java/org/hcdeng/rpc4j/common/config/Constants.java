package org.hcdeng.rpc4j.common.config;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class Constants {
    public static final String ZK_ADDRESS = "registry.zookeeper.address";
    public static final String ZK_SERVICE_PATH_PREFIX = "/rpc4j/service";
    public static final String ZK_SERVICE_PATH_FORMAT = ZK_SERVICE_PATH_PREFIX + "/%s";

    public static final String SERVER_HOST_CONFIG = "server.host";
    public static final String SERVER_PORT_CONFIG = "server.port";

    public static final int DEFAULT_SERVER_PORT = 4080;
}
