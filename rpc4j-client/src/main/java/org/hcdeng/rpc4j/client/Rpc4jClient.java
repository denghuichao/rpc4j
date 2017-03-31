package org.hcdeng.rpc4j.client;

import com.google.common.util.concurrent.SettableFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hcdeng.rpc4j.common.entity.RpcRequset;
import org.hcdeng.rpc4j.common.entity.RpcResponse;
import org.hcdeng.rpc4j.common.serialize.RpcDecoder;
import org.hcdeng.rpc4j.common.serialize.RpcEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hcdeng.rpc4j.common.entity.ServiceProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class Rpc4jClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rpc4jClient.class);

    private ServiceProvider serviceProvider;

    public Rpc4jClient(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public RpcResponse send(RpcRequset requset) throws InterruptedException, ExecutionException, TimeoutException{
        LOGGER.info("client sending rpc request...");
        final SettableFuture<RpcResponse> future = SettableFuture.create();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder(), new RpcEncoder(), new Rpc4jClientHandler(future));
                        }
                    });

            ChannelFuture f = bootstrap.connect(serviceProvider.getHost(),serviceProvider.getPort()).sync();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    LOGGER.info("client success to connect the server!");
                }
            });

            ChannelFuture wf = f.channel().writeAndFlush(requset).sync();
            wf.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    LOGGER.info("client complete to write the request");
                }
            });

            return future.get(1000, TimeUnit.MILLISECONDS);

        } finally {
            group.shutdownGracefully();
        }

    }
}
