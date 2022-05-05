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


    public ReceiveThread(String host){
        try {

            ServerSocket serverSocket = new ServerSocket(Main.port);
            socket = serverSocket.accept();
            ObjectInputStream inputStream = (ObjectInputStream) socket.getInputStream();
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }

    public void run(){
        while (inputStream != null && !socket.isClosed()){
            try {
                TestMessage message = (TestMessage) inputStream.readObject();
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
