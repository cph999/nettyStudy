package netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class nettyClient {
    //客户端需要一个事件循环组
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        //创建启动对象
        Bootstrap bootstrap = new Bootstrap();

        try{
            //设置相关参数
            bootstrap.group(eventExecutors)     //设置线程组
                    .channel(NioSocketChannel.class)    //设置客户端通道实现类
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new nettyClientHandler()); //加入自己的处理器
                        }
                    });
            System.out.println("客户端ok...");

            //启动客户端去连接服务器端
            //关于ChannelFuture，涉及到netty的异步模型
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();
            cf.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
