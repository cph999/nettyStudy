package netty.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import netscape.javascript.JSObject;

import java.time.LocalTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 崇鹏豪
 * @Date: 2022/07/30/12:45
 * @Description:
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Message message = JSONObject.parseObject(msg.text(), Message.class);
        System.out.println("服务器收到来自"+ ctx.channel().remoteAddress() +"的消息:"+ message);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:" + LocalTime.now() + message));
    }

    /**
     * 当web客户端连接后触发
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded被调用了" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
