package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.groupchat.GroupChatServerHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 崇鹏豪
 * @Date: 2022/07/30/12:35
 * @Description:
 */
public class MyServer {
    private int port;

    public MyServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给pipeline指定相关的handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //使用http编码解码器
                            pipeline.addLast(new HttpServerCodec());
                            //使用块的形式通信
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * http的大数据在传输过程中可能分成多次请求，HttpObjectAggregator将多个段聚合起来
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 对于websocket，数据依frame传输
                             * 浏览器请求时 ws://localhost:7000/xxx
                             * 核心是将http升级为ws协议
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new MyWebSocketHandler());
                        }
                    });
            System.out.println("netty 服务器启动");
            ChannelFuture cf = serverBootstrap.bind(port).sync();
            //监听关闭
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new MyServer(6688).run();
    }
}
