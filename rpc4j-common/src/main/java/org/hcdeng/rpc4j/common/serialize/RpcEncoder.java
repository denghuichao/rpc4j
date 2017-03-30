package org.hcdeng.rpc4j.common.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcEncoder extends MessageToByteEncoder{
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        LOGGER.info("encoding start...");
        byte[] bytes = Serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        LOGGER.info("finish encode!");
    }
}
