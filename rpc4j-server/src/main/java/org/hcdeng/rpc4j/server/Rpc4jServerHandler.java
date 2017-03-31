package org.hcdeng.rpc4j.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hcdeng.rpc4j.common.entity.RpcRequset;
import org.hcdeng.rpc4j.common.entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by hcdeng on 2017/3/31.
 */
public class Rpc4jServerHandler extends SimpleChannelInboundHandler<RpcRequset>{
    private static Logger LOGGER = LoggerFactory.getLogger(Rpc4jServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequset msg) throws Exception {
        Object impl = Rpc4jServiceServer.getActualImpl(msg.getServiceName());
        if(impl != null){
            RpcResponse response = new RpcResponse();
            response.setRequestId(msg.getRequestId());
            Class<?> serviceClass = impl.getClass();
            Method method = serviceClass.getMethod(msg.getMethodName(), msg.getParaTypes());
            Object invokeResult = method.invoke(impl, msg.getArguments());
            response.setResponseObject(invokeResult);

            LOGGER.info("server invoke result: "+invokeResult);

            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    LOGGER.info("server write response, result: "+future.isSuccess());
                }
            });
        }
        else{
            LOGGER.info("can not find actual for "+msg.getServiceName());
        }
    }
}
