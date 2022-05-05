public class Message {

    public byte[] byte_array;
    public  String file_name;

    public Message(String file_name, byte[] byte_array){
        this.file_name = file_name;
        this.byte_array = byte_array;
    }
}
