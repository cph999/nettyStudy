package nio.nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {

        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到selector对象
        Selector selector = Selector.open();

        //绑定端口，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector中，0表示不关心这个channel
        serverSocketChannel.register(selector, 0);

        //循环等待客户端链接
        while (true){
            //监听1s，如果没有响应就向下执行
            if(selector.select(1000) == 0){
                //没有事件发生
                System.out.println("服务器等待1s，无连接");
                continue;
            }

            //如果返回>0,就取到相关的selectionKey集合（有事件发生的集合）
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历selectionKeys
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){  //OP_ACCEPT
                    //把后续连接的channel全部注册到selector中，让selector监视channle
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    //如果是链接事件，那么下面关注的是读事件，将serverSocketChannel注册到selector并关注读事件，关联buffer
                    channel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){
                    //通过key反向获取channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //获取到该channel对应的buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    int read = channel.read(buffer);
                    System.out.println("from client" + new String(buffer.array()));
                }
                //手动删除当前selectionKey，防止重复操作
                iterator.remove();
            }
        }
    }
}
