package dev.aco.back.DirectMessage;

import lombok.Data;

@Data
public class ChatMessage {
    public enum Status {
        JOIN,
        MESSAGE,
        LEAVE
    }
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;


}
