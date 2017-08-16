package org.hcdeng.rpc4j.common.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Splitter;
import org.hcdeng.rpc4j.common.config.Constants;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class ZKPathUtils {

    //provider node should be like "/rpc4j/service/serviceName/127.0.0.1:4080"
    private static final String IP_PATTERN = "((?:(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d))\\.){3}(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d)))";
    private static final String ZK_PROVIDER_NODE_PATTERN = Constants.ZK_SERVICE_PATH_PREFIX + "/" + "[^/]+/" + IP_PATTERN + ":\\d+";

    //service node should be like "/rpc4j/service/serviceName"
    private static final int ZK_SERVICE_NODE_DEPTH = 3;

    public static String formatProviderPath(String serviceName, String host, int port) {
        checkNotNull(serviceName);
        checkNotNull(host);
        checkArgument(port >= 0);

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ZK_SERVICE_PATH_PREFIX).append("/");
        sb.append(serviceName).append("/").
                append(host).append(":").append(port);

        return sb.toString();
    }

    public static boolean isValidProviderPath(String path) {
        checkNotNull(path);
        return path.matches(ZK_PROVIDER_NODE_PATTERN);
    }

    public static String getServiceNameFromProviderPath(String providerPath) {
        if (!isValidProviderPath(providerPath))
            throw new IllegalArgumentException(providerPath + "is not a valid provider path!");

        return Splitter.on("/").splitToList(providerPath).get(ZK_SERVICE_NODE_DEPTH);
    }

}
