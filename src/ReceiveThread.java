import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class ReceiveThread extends Thread{

    private Socket socket;
    ObjectInputStream inputStream;


    public ReceiveThread(){
        try {
            ServerSocket serverSocket = new ServerSocket(Main.port);
            socket = serverSocket.accept();
            System.out.println("Server connected!!");
            ObjectInputStream inputStream = (ObjectInputStream) socket.getInputStream();
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }

    public void run(){
        System.out.println("Receive Thread Listening");
        while (inputStream != null && !socket.isClosed()){
            try {
                TestMessage message = (TestMessage) inputStream.readObject();
                System.out.println(message.message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
