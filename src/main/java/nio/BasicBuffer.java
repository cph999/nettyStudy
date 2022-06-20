package nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(10);
        //读写卡换
        intBuffer.flip();
        System.out.println(intBuffer.get());
    }
}
