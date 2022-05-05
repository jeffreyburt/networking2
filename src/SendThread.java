import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class SendThread extends Thread{

    private Socket socket;
    public ObjectOutputStream send_output_stream;
    private LinkedBlockingQueue<TestMessage> messagesToSend;

    public SendThread(String host, LinkedBlockingQueue<TestMessage> messagesToSend){
        try {
            this.messagesToSend = messagesToSend;
            socket = new Socket(host, Main.port);
            send_output_stream = (ObjectOutputStream) socket.getOutputStream();
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }

    public void run(){
        while (true){
            try {
                TestMessage message = messagesToSend.take();
                send_output_stream.writeObject(message);
                send_output_stream.flush();
                send_output_stream.reset();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }



}
