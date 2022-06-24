package netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.stream.IntStream;

public class NettyByteBuf01 {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        IntStream.range(0,100).forEach(buffer::writeByte);
        //输出
        for(int i = 0; i < buffer.capacity(); i++){
            System.out.println(buffer.getByte(i));
        }
    }
}
