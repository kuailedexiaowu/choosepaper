package lut.kj.choosepaper.core;

/**
 * Created by kj on 2017/1/31.
 */
public class Message {
    private String message=null;

    public Message(String message) {
        this.message = message;
    }

    public Message() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  message: ").append(message).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
