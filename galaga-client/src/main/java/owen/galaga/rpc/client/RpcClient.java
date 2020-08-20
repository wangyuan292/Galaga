package owen.galaga.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import owen.galaga.rpc.common.base.RpcRequest;
import owen.galaga.rpc.common.base.RpcResponse;
import owen.galaga.rpc.common.coder.RpcDecoder;
import owen.galaga.rpc.common.coder.RpcEncoder;

/**
 * @author Owen.Wang
 * @description: RPC客户端
 * @date 2020/5/30 19:58
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private int port;
    private String host;

    private RpcResponse response;

    private final Object object = new Object();

    public RpcClient() {
    }

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        LOGGER.info("响应 {}", null == response ? null : response.toString());
        this.response = response;
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {
        boolean isDone = false;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                    ch.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                    ch.pipeline().addLast(RpcClient.this);
                }
            });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            LOGGER.debug("打开服务 {}:{} -> {}", host, port, future.channel().isOpen());
            future.channel().writeAndFlush(request);
            // 检测服务提供方是否成功返回了调用结果
            while (!isDone) {
                if (null == response) {
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    isDone = true;
                }
            }
            return response;
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("client caught exception", cause);
        ctx.close();
    }

}
