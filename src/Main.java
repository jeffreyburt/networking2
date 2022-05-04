import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;



public class Main {

    private static File selectedFile;
    private static byte[] fileByteArray;

    public static void main(String[] args) throws IOException {
        displayEntryDialogue();
        System.out.println(selectedFile + " selected");
        selectedFile.setReadable(true);
        fileByteArray = getFileByteArray(selectedFile);


        fileSaveDialogue();
    }

    private static void displayEntryDialogue() throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//
//        System.out.println("Hello, Welcome to JTP (Jeffrey Transfer Protocol");
//        System.out.println("Please enter the IP you would like to connect to, or leave the field blank to receive a connection");
//        System.out.print("Enter IP here: ");
//        String ip = reader.readLine();
//        if(ip.isEmpty()){
//            System.out.println("Blank input detected, awaiting connection...");
//        } else {
//            System.out.println("Attempting to connect to " + ip + " ...");
//        }
        selectedFile = fileSelector();
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
