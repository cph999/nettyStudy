package nio.nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器的ip和port
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if(!socketChannel.connect(socketAddress)){
            while(!socketChannel.finishConnect()){
                System.out.println("因为链接需要时间，客户端不会阻塞,可以做其他工作...");
            }
        }
        //如果连接成功就发送数据
        String str = new String("hello,爱情来得太快就像龙卷风");
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes("UTF8"));
        //发送数据(将buffer写入channel)
        socketChannel.write(buffer);
        System.in.read();
    }
}
