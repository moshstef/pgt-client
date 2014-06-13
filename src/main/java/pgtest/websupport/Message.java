package pgtest.websupport;


import java.io.Serializable;

public final class Message implements Serializable {
    public static final String MESSAGE_KEY = "message";

    private final MessageType type;

    private final String text;

    public Message(MessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String toString() {
        return type + ": " + text;
    }

}
