package com.Satvik.chat.chatapp_backend.Service;

import com.Satvik.chat.chatapp_backend.Entity.Message;
import com.Satvik.chat.chatapp_backend.Entity.Room;
import com.Satvik.chat.chatapp_backend.Payload.MessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {
    boolean findroom(String roomId);

    ResponseEntity<?> SaveRoom(String roomId);

    List<Room> getRoom();

    Room getRoomById(long id);

    Room findByRoomId(String roomId);

    ResponseEntity<List<Message>> getMessages(String roomId, int page, int size);

    ResponseEntity<?> addMessageToRoom(String roomId, MessageRequest messageRequest);
}
