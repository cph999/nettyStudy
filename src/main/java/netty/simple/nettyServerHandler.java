package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//自定义一个pipeline对应的handler
public class nettyServerHandler extends ChannelInboundHandlerAdapter {
    //客户端消息发送过来，将信息传送到pipeline对应的handler

    /**
     *
     * @param ctx 上下文对象，含有 管道pipeline（可以关联很多handler），通道channel
     * @param msg 客户端发送的数据，默认为object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //pipeline本质上是个双向链表
        //将msg转换为ByteBuf
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端发送消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }


    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写到缓冲区并且flush（write + flush）
        //对数据进行能编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,崇鹏豪",CharsetUtil.UTF_8));
    }

    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
