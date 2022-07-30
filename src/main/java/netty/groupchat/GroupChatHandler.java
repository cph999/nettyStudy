package netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.groupchat.entity.MessagePojo;

public class GroupChatHandler extends SimpleChannelInboundHandler<MessagePojo.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePojo.Message msg) throws Exception {
        System.out.println(msg.getUsername()+": "+ msg.getContent());
    }
}
