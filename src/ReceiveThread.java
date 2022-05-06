import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ReceiveThread extends Thread{

    private Socket socket;
    ObjectInputStream inputStream;


    public ReceiveThread(){
        try {
            ServerSocket serverSocket = new ServerSocket(Main.port);
            socket = serverSocket.accept();
            System.out.println("Server connected!!");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            inputStream = new ObjectInputStream(bufferedInputStream);
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }

    public void run(){
        System.out.println("Receive Thread Listening");
        while (inputStream != null){
            System.out.println("input stream looped");
            try {
                Message message = (Message) inputStream.readObject();
                System.out.println("Received file " + message.file_name);
                Main.receivedMessages.put(message);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
