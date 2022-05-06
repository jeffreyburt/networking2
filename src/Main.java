import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static int port = 12345;
    private static String host;

    public static LinkedBlockingQueue<TestMessage> messagesToSend;
    private static SendThread sendThread;
    private static ReceiveThread receiveThread;

    private static File selectedFile;
    private static byte[] fileByteArray;
    private static boolean is_client;

    public static void main(String[] args) throws IOException, InterruptedException {
        displayEntryDialogue();

    }

    private static void displayEntryDialogue() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hello, Welcome to JTP (Jeffrey Transfer Protocol");
        System.out.println("Please enter the IP you would like to connect to, or leave the field blank to receive a connection");
        System.out.print("Enter IP here: ");
        host = reader.readLine();
        if(host.isEmpty()){
            System.out.println("Blank input detected, awaiting connection...");
            is_client = false;
        } else {
            System.out.println("Attempting to connect to " + host + " ...");
            is_client = true;
            clientSetup();
        }
    }

    private static void serverSetup(){
        receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    private static void clientSetup() throws IOException, InterruptedException {
        messagesToSend = new LinkedBlockingQueue<>();
        sendThread = new SendThread(host, messagesToSend);
        sendThread.start();

        selectedFile = fileSelector();
        System.out.println(selectedFile.getName() + " selected");
        selectedFile.setReadable(true);
        fileByteArray = getFileByteArray(selectedFile);

        Message message = new Message(selectedFile.getName(), fileByteArray);
        //messagesToSend.put(message);

        TestMessage testMessage = new TestMessage("please work");
        messagesToSend.put(testMessage);
    }



    private static File fileSelector(){
        System.out.println("file selector called");
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        System.out.println("Oops, there was an error selecting the file");
        return null;
    }

    private static byte[] getFileByteArray(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private static File fileSaveDialogue() throws IOException {
        System.out.println("file saver called");
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            System.out.println(fileChooser.getSelectedFile().getName());
            fileChooser.getSelectedFile().createNewFile();
            try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())){
                fos.write(fileByteArray);
            }
        }
        return null;
    }



}
