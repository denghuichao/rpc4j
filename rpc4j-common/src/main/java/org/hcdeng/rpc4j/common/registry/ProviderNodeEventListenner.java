package org.hcdeng.rpc4j.common.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.hcdeng.rpc4j.common.utils.ZKPathUtils;

/**
 * Created by hcdeng on 2017/3/30.
 * zookeeper节点事件监听，节点的子节点变化事件触发childEvent
 */
public class ProviderNodeEventListenner implements TreeCacheListener{
    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        if(isProviderNodeEvent(treeCacheEvent)){
            String serviceName = ZKPathUtils.getServiceNameFromProviderPath(treeCacheEvent.getData().getPath());
            ProviderListennerManager.instance().onProviderChange(serviceName);
        }
    }

    /**
     * 判断事件是否为rpc4j节点新增事件
     * @param treeCacheEvent
     * @return boolean
     */
    private boolean isProviderNodeEvent(TreeCacheEvent treeCacheEvent){
        if(treeCacheEvent == null)return false;

        if(treeCacheEvent.getType() == TreeCacheEvent.Type.NODE_ADDED) {
            String path = treeCacheEvent.getData().getPath();
            if (ZKPathUtils.isValidProviderPath(path))
                return true;
        }
        return false;
    }
}
