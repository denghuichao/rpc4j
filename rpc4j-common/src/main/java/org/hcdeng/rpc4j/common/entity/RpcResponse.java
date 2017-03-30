package org.hcdeng.rpc4j.common.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcResponse implements Serializable{
    private long requestId;
    private int resultCode;
    private Object responseObject;

    public RpcResponse() {}

    public RpcResponse(long requestId, int resultCode, Object responseObject) {
        this.requestId = requestId;
        this.resultCode = resultCode;
        this.responseObject = responseObject;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}
