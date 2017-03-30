package org.hcdeng.rpc4j.client.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import org.hcdeng.rpc4j.client.Rpc4jClient;
import org.hcdeng.rpc4j.client.provider.ProviderManager;
import org.hcdeng.rpc4j.common.entity.RpcRequset;
import org.hcdeng.rpc4j.common.entity.RpcResponse;
import org.hcdeng.rpc4j.common.entity.ServiceProvider;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcProxy extends AbstractInvocationHandler{

    private String serviceName;

    public RpcProxy(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        ServiceProvider provider =  ProviderManager.getProvider(serviceName);

        RpcRequset requset = new RpcRequset();
        requset.setRequestId(UUID.randomUUID().toString());
        requset.setServiceName(serviceName);
        requset.setMethodName(method.getName());
        requset.setParaTypes(method.getParameterTypes());
        requset.setArguments(args);

        Rpc4jClient rpc4jClient = new Rpc4jClient(provider);
        RpcResponse response = rpc4jClient.send(requset);

        return response.getResponseObject();
    }
}
