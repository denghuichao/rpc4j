package org.hcdeng.rpc4j.client;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hcdeng.rpc4j.common.entity.RpcResponse;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class Rpc4jClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private SettableFuture<RpcResponse> future;

    public Rpc4jClientHandler(SettableFuture<RpcResponse> future){
        this.future = future;
    }

    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        future.set(msg);
    }
}
