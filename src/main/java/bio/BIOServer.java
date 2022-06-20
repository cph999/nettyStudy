package bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        /*
          线程池机制
          1.创建线程池
          2.如果客户端有链接，就创建一个线程，与之通讯
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while(true){
            //监听，等待客户端链接
            final Socket socket = serverSocket.accept();
            System.out.println("客户端连接到服务器" + socket.getInetAddress() + socket.getPort());
            executorService.execute(() -> {
                BIOServer.handler(socket);
            });
        }
    }

    public static void handler(Socket socket){
        byte[] bytes = new byte[1024];
        //通过socket获取输入流
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            InputStream inputStream = socket.getInputStream();

            String s = (String) objectInputStream.readObject();
            System.out.println(s + " *** ");
            //循环读取客户端的数据
            while(true){
                int read = inputStream.read(bytes);
                if(read != -1){
                    //输出客户端发送的数据
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("关闭和客户端的链接");
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
