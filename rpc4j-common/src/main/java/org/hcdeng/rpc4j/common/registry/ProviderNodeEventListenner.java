package org.hcdeng.rpc4j.common.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.hcdeng.rpc4j.common.utils.ZKPathUtils;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class ProviderNodeEventListenner implements TreeCacheListener{
    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        if(isProviderNodeEvent(treeCacheEvent)){
            String serviceName = ZKPathUtils.getServiceNameFromProviderPath(treeCacheEvent.getData().getPath());
            ProviderListennerManager.instance().onProviderChange(serviceName);
        }
    }

    private boolean isProviderNodeEvent(TreeCacheEvent treeCacheEvent){
        if(treeCacheEvent == null)return false;

        String path = treeCacheEvent.getData().getPath();
        if(!ZKPathUtils.isValidProviderPath(path))return false;

        if(treeCacheEvent.getType() == TreeCacheEvent.Type.NODE_ADDED)
            return true;

        return false;
    }

}
