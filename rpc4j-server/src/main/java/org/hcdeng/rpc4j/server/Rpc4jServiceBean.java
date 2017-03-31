package org.hcdeng.rpc4j.server;

/**
 * Created by hcdeng on 2017/3/31.
 */
public class Rpc4jServiceBean {
    private String serviceName;
    private Object actualImpl;

    public void init(){
        Rpc4jServiceServer.addService(serviceName, actualImpl);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getActualImpl() {
        return actualImpl;
    }

    public void setActualImpl(Object actualImpl) {
        this.actualImpl = actualImpl;
    }
}
