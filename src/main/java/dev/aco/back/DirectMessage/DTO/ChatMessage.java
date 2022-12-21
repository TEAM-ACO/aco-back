package dev.aco.back.DirectMessage.DTO;

import lombok.Data;

@Data
public class ChatMessage {
    private Long msgId;
    private String context;
    private Long sender;
    private Boolean readed;
    private Long msgroomId;
}
