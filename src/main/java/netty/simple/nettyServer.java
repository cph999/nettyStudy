package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.concurrent.ExecutorService;

public class nettyServer {
    public static void main(String[] args) throws InterruptedException {

        //创建 BossGroup和 WorkerGroup
        //创建两个线程组，BossGroup和和WorkerGroup，BossGroup只处理连接请求，处理客户端业务交给WorkerGroup完成
        //bossGroup workerGroup默认子线程个数是 cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器启动的对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup) //配置两个线程组
                    .channel(NioServerSocketChannel.class)  //使用NioServerSocketChannel作为服务器通道的实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象
                        //向pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //返回socketChannel关联的pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            socketChannel.pipeline().addLast(new nettyServerHandler());
                        }
                    }); //给workerGroup的eventLoop对应的pipeline（管道）设置处理器，（可以使用netty提供的，或者自己写）
            System.out.println("server is ready...");

            //绑定一个端口并且同步处理
            ChannelFuture cf = serverBootstrap.bind(6668).sync();
            //监听关闭通道
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
