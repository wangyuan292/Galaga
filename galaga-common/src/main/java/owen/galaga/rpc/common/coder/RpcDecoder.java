package owen.galaga.rpc.common.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import owen.galaga.rpc.common.util.SerializationUtil;

import java.util.List;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 19:11
 */
public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> aClass;       // 由于不知道需要解码的类是什么，所以使用？无限定通配符。 也可通过定义泛型T来实现

    public RpcDecoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4){      // 可读字节不够长度
            return;
        }
        in.markReaderIndex();      // 设置读开始的标志位，当数据无法完成业务时，可复位
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength){
            in.resetReaderIndex();       // 复位
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, aClass));
    }
}

