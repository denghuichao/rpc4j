package org.hcdeng.rpc4j.common.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class RpcDecoder  extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcEncoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        LOGGER.info("decoding start...");

        if(in.readableBytes() < 4){
            LOGGER.info("no enough bytes to read");
            return;
        }

        int dataLen = in.readInt();
        if(dataLen < 0){
            ctx.close();
            return;
        }

        if(in.readableBytes() < dataLen){
            in.resetReaderIndex();
        }

        byte[] bytes = new byte[dataLen];
        in.readBytes(bytes);
        out.add(Serializer.deserialize(bytes));

        LOGGER.info("finish decode");
    }
}
