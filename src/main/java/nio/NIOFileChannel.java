package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        //循环读取文件数据
        while(true){
            //把buffer重置（清空数据）
            byteBuffer.clear();
            int read = channel1.read(byteBuffer);
            if(read == -1){
                break;
            }else {
                //buffer中的数据写道channel2
                byteBuffer.flip();
                channel2.write(byteBuffer);
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
