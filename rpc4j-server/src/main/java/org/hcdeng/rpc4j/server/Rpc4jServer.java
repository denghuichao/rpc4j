package org.hcdeng.rpc4j.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.hcdeng.rpc4j.common.serialize.RpcDecoder;
import org.hcdeng.rpc4j.common.serialize.RpcEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by hcdeng on 2017/3/31.
 */
public class Rpc4jServer {

    private static Logger LOGGER = LoggerFactory.getLogger(Rpc4jServer.class);

    private static volatile boolean started = false;

    public static void startUp(int port){
        if(!started){
            synchronized (Rpc4jServer.class){
                if(!started) bootstrap(port);
            }
        }
    }

    private static void bootstrap(int port){
        EventLoopGroup connectEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(connectEventLoopGroup, workEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder(), new RpcEncoder(), new Rpc4jServerHandler());
                        }
                    });
            ChannelFuture f = bootstrap.bind(port).sync();
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        started = true;
                        LOGGER.info("success to start the rpc4j server");
                    }
                }
            });
        }catch (InterruptedException e){
           LOGGER.warn("fail to start the rpc4j server: "+e.getMessage());
        }
    }
}
