package org.hcdeng.rpc4j.common.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcRequset implements Serializable{
    private String requestId;
    private String serviceName;
    private String methodName;
    private Class<?>[] paraTypes;
    private Object[] arguments;

    public RpcRequset() {}

    public RpcRequset(String requestId, String serviceName, String methodName, Class<?> []paraTypes, Object[] arguments) {
        this.requestId = requestId;
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.paraTypes = paraTypes;
        this.arguments = arguments;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParaTypes() {
        return paraTypes;
    }

    public void setParaTypes(Class<?>[] paraTypes) {
        this.paraTypes = paraTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
