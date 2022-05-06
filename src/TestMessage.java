import java.io.Serializable;

public class TestMessage implements Serializable {

    public String message;

    public TestMessage(String message){
        this.message = message;
    }
}
