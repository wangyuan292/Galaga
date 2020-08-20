package owen.galaga.rpc.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import owen.galaga.rpc.common.util.SerializationUtil;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 20:03
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> aClass;

    public RpcEncoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf byteBuf) throws Exception {
        if (aClass.isInstance(in)){
            byte[] bytes = SerializationUtil.serialize(in);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }

    }
}
