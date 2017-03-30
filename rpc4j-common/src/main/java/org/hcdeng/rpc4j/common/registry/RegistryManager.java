package org.hcdeng.rpc4j.common.registry;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.hcdeng.rpc4j.common.config.ConfigManager;
import org.hcdeng.rpc4j.common.config.Constants;
import org.hcdeng.rpc4j.common.entity.ServiceProvider;
import org.hcdeng.rpc4j.common.utils.ZKPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class RegistryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryManager.class);

    private static Executor curatorThreadPoool = Executors.newFixedThreadPool(10);

    private static CuratorFramework client;

    private static volatile boolean started = false;

    public static void startUp()throws Exception{
        if(!started){
            synchronized (RegistryManager.class){
                String zkAddress = ConfigManager.instance().getProperty(Constants.ZK_ADDRESS);
                RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
                client = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
                client.start();

                TreeCache cache = TreeCache.newBuilder(client, Constants.ZK_SERVICE_PATH_PREFIX).setCacheData(false).build();

                //节点变化事件监听器注册
                cache.getListenable().addListener(new ProviderNodeEventListenner(), curatorThreadPoool);
                cache.start();
                started = client.blockUntilConnected(1000, TimeUnit.MICROSECONDS);
            }
        }
    }

    public static boolean publishService(String serviceName, String host, int port){
        if(!started)
            throw new IllegalStateException("the rigister is not started yet!");

        if(Strings.isNullOrEmpty(host))
            throw new IllegalArgumentException("the host must not be empty!");

        String serverPath = ZKPathUtils.formatProviderPath(serviceName, host,port);
        try {
            String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(serverPath);
            if (Strings.isNullOrEmpty(s)) {
                LOGGER.warn("error when add service to zk:" + s);
                return false;
            }
            return true;
        }catch (Exception e){
            LOGGER.warn("error when add service to zk:" + e.getMessage());
            return false;
        }
    }

    /**
     * 加载一个服务所有可用provider信息
     * @param serviceName
     * @return
     */
    public static List<ServiceProvider> loadProviders(String serviceName){
        if(!started)
            throw new IllegalStateException("the rigister is not started yet!");

        try {
            List<String> list = client.getChildren().watched().forPath(String.format(Constants.ZK_SERVICE_PATH_FORMAT, serviceName));
            List<ServiceProvider> providers = Lists.transform(list, (serverPath) -> {
                if (Strings.isNullOrEmpty(serverPath)) return null;
                List<String> strs = Splitter.on(":").splitToList(serverPath);
                if (CollectionUtils.isNotEmpty(strs) && strs.size() == 2)
                    return new ServiceProvider(strs.get(0), Integer.parseInt(strs.get(1)));

                return null;
            });
            return providers;
        }catch (Exception e){
            return Collections.EMPTY_LIST;
        }
    }
}
