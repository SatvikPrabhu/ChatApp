package com.Satvik.chat.chatapp_backend.Controller;

import com.Satvik.chat.chatapp_backend.Entity.Message;
import com.Satvik.chat.chatapp_backend.Entity.Room;
import com.Satvik.chat.chatapp_backend.Payload.MessageRequest;
import com.Satvik.chat.chatapp_backend.Repository.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request) {
        Room room = roomRepository.findByRoomId(request.getRoomId()).orElse(null);
        if (room == null) {
            throw new RuntimeException("Room not found: " + roomId);
        }
        Message message = new Message(request.getSender(), request.getContent());
        message.setTimeStamp(LocalDateTime.now());
        room.getMessages().add(message);
        roomRepository.save(room);
        return message;
    }
}
