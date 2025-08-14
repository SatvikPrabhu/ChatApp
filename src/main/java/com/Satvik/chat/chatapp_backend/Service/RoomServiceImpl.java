package com.Satvik.chat.chatapp_backend.Service;

import com.Satvik.chat.chatapp_backend.Entity.Message;
import com.Satvik.chat.chatapp_backend.Entity.Room;
import com.Satvik.chat.chatapp_backend.Payload.MessageRequest;
import com.Satvik.chat.chatapp_backend.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public boolean findroom(String roomId) {
        return roomRepository.findByRoomId(roomId).isPresent();
    }

    @Override
    public ResponseEntity<?> SaveRoom(String roomId) {
        if (findroom(roomId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room already exists!");
        }
        Room room = new Room();
        room.setRoomId(roomId);
        roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @Override
    public List<Room> getRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room findByRoomId(String roomId) {
        return roomRepository.findByRoomId(roomId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<Message>> getMessages(String roomId, int page, int size) {
        Room room = findByRoomId(roomId);
        if (room == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<Message> messages = room.getMessages();
        if (messages == null || messages.isEmpty()) return ResponseEntity.ok(Collections.emptyList());

        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return ResponseEntity.ok(messages.subList(start, end));
    }

    @Override
    public ResponseEntity<?> addMessageToRoom(String roomId, MessageRequest messageRequest) {
        Room room = findByRoomId(roomId);
        if (room == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");

        Message message = new Message(messageRequest.getSender(), messageRequest.getContent());
        message.setTimeStamp(LocalDateTime.now());

        room.getMessages().add(message);
        roomRepository.save(room);

        return ResponseEntity.ok(message);
    }
}
