package bio;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BIOClient {

    public static void main(String[] args) throws IOException {
        while(true) {
            Socket socket = new Socket("127.0.0.1", 6666);
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(s);
            objectOutputStream.flush();
        }
    }
}
