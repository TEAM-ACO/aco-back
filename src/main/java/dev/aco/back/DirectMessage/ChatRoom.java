package dev.aco.back.DirectMessage;

import java.util.UUID;

import lombok.Data;

@Data
public class ChatRoom {
    private String roomId;
    private Long userone;
    private Long usertwo;

    public static ChatRoom create(Long one, Long two){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.userone = one;
        chatRoom.usertwo = two;
        return chatRoom;
    }
}
