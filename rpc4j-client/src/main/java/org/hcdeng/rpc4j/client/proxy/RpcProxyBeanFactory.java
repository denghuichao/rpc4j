package org.hcdeng.rpc4j.client.proxy;

import com.google.common.reflect.Reflection;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcProxyBeanFactory {
    public static Object getService(String serviceName)throws ClassNotFoundException{
        Class<?> className = Class.forName(serviceName);
        return Reflection.newProxy(className, new RpcProxy(serviceName));
    }
}
