import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class SendThread extends Thread{

    private Socket socket;
    public ObjectOutputStream send_output_stream;
    private LinkedBlockingQueue<Message> messagesToSend;

    public SendThread(String host, LinkedBlockingQueue<Message> messagesToSend){
        try {
            this.messagesToSend = messagesToSend;
            socket = new Socket(host, Main.port);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            send_output_stream = new ObjectOutputStream(bufferedOutputStream);
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }

    public void run(){
        while (true){
            try {
                Message message = messagesToSend.take();
                System.out.println("Sending file named: " + message.file_name);
                send_output_stream.writeObject(message);
                send_output_stream.flush();
                send_output_stream.reset();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }



}
